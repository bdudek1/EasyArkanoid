package com.example.arkanoid.client.util;

import java.util.Set;
import java.util.logging.Level;

import com.example.arkanoid.client.config.Configuration;
import com.example.arkanoid.client.sprites.Ball;
import com.example.arkanoid.client.sprites.Brick;
import com.example.arkanoid.client.sprites.Paddle;
import com.example.arkanoid.client.util.input.InputProcessor;
import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.animation.client.AnimationScheduler.AnimationCallback;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;

public class GameThread {
	private Context2d context;
	private GameState gameState;
	private byte lives;
	
	private Paddle paddle;
	private Ball ball;
	private Set<Brick> brickSet;
	
	private Timer timer;
	private double time = 0;
	private double lag = 0;
	private double timeElapsed;
	
	public GameThread(Canvas canvas) {
		addFocusHandlers(canvas);
		context = canvas.getContext2d();
	}
	
	public Paddle getPaddle() {
		return paddle;
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	
	private void gameOver() {
		setGameState(GameState.GAME_OVER);
		GameUtil.gameOver();
	}
	
	private void win() {
		setGameState(GameState.WON);
		GameUtil.win();
	}
	
	private void setLives(byte lives) {
		this.lives = lives;
		GameUtil.updateLivesDisplay(lives);
	}
	
	private void uptadeTimeElapsed(double timestamp) {
		timeElapsed = timeElapsed + timestamp;
		GameUtil.updateTimeDisplay(timeElapsed);
		if(timeElapsed > Configuration.LEVEL_TIMEOUT) {
			gameOver();
		}
	}
	
	private void update() {
		GameUtil.updateSpritesPosition(paddle, ball);
		GameUtil.checkAndProcessBallCollisions(ball, brickSet, paddle);
		if(!ball.checkIfIsInsideCanvas()) {
			loseLife();
		}
	    if(brickSet.size() == 0) {
	    	win();
	    }
		uptadeTimeElapsed(Configuration.MS_PER_UPDATE);
	}
	
	private void render(double positionExtrapolationAdjustment) {
		GameUtil.clearCanvas(context);
		GameUtil.drawSprites(context, paddle, ball, brickSet, positionExtrapolationAdjustment);
	}
	
	private void processInput() {
		InputProcessor.processInputQueue(paddle);
	}
	
	private void initializeLevel() {
		paddle = SpritesInitializer.createPaddle(Configuration.CANVAS_WIDTH/2,
												 Configuration.CANVAS_HEIGHT-30);
		brickSet = SpritesInitializer.createBricks(Configuration.getBrickRowsNumber(),
												   Configuration.getBrickColumnsNumber());
		ball = SpritesInitializer.createBall();
		setLives((byte)3);
		timeElapsed = 0L;
	}
	
	//dodaje do canvas wykrywanie utraty focusu i automatyczne pauzowanie
	//z tym zwiazane
	private void addFocusHandlers(Canvas canvas) {
		canvas.addFocusHandler(new FocusHandler() {
	        @Override
	        public void onFocus(FocusEvent event) {
	            if(getGameState() == GameState.PAUSED) {
	            	setGameState(GameState.RUNNING);
	            	AnimationScheduler.get().requestAnimationFrame(gameLoop);
	            }
	        }
	    });
		canvas.addBlurHandler(new BlurHandler() {
	        @Override
	        public void onBlur(BlurEvent event) {
	        	if(getGameState() == GameState.RUNNING)
	        		setGameState(GameState.PAUSED);
	        }
	    });
	}
	
	//zdarzenie utraty zycia gdy pilka spadnie i sprawdzenie czy gracz przegral
	private void loseLife() {
		setLives((byte) (lives-1));
		ball.hide();
		if(lives>0) {
			//2 sekundy na przygotowanie nowej pilki po utracie zycia
			timer = new Timer() {
				@Override
				public void run() {
					ball = SpritesInitializer.createBall();
				}
			};
			timer.schedule(2000);
			SoundBoard.playLoseLifeSound();
			Configuration.log(Level.INFO, "Lost life, lives left: " + lives);
		}else {
			gameOver();
		}
	}

	public void startGame() {
		initializeLevel();
		setGameState(GameState.RUNNING);
		RootPanel.get(Configuration.CANVAS_HOLDER_ID).get("gameover").clear();
		SoundBoard.playStartSound();
		Configuration.log(Level.INFO, "Game started");
		AnimationScheduler.get().requestAnimationFrame(gameLoop);
	}
	
	//glowna petla gry
	//lag co kazde wykonanie rekurencji animacji jest inkrementowany przez czas wykonania
	//funkcji processInput() oraz update() i jesli bedzie wiekszy od czasu
	//Configuration.MS_PER_UPDATE, czyli czasu miedzy tikami animacji to stan gry bedzie
	//zaktualizowany tyle razy, o ile razy wiekszy jest lag od Configuration.MS_PER_UPDATE
	
	//do funkcji render() jest przekazywana wartosc lag/Configuration.MS_PER_UPDATE, ktora
	//pokazuje, jak blisko jestesmy do kolejnej, wyzej opisanej korekcji laga, im blizej korekcji
	//jestesmy, tym bardziej wezmiemy pod uwage predkosc pozioma i pionowa przy rysowaniu
	//sprite'ow, ktore maja mozliwosc poruszania sie, dzieki czemu gra wyglada na bardziej plynna
	private AnimationCallback gameLoop = new AnimationCallback() {
		@Override
		public void execute(double timestamp) {
		  	  if(getGameState().equals(GameState.RUNNING)) {
					time = System.currentTimeMillis();
					processInput();
					update();
					lag += System.currentTimeMillis() - time;
					while(lag > Configuration.MS_PER_UPDATE) {
						update();
						lag =- Configuration.MS_PER_UPDATE;
					}
					render(lag/Configuration.MS_PER_UPDATE);
					AnimationScheduler.get().requestAnimationFrame(this);
		  	  }  
	     };
	};
}
