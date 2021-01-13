package com.example.arkanoid.client.util;

import java.util.Set;
import java.util.logging.Level;

import com.example.arkanoid.client.config.Configuration;
import com.example.arkanoid.client.sprites.Ball;
import com.example.arkanoid.client.sprites.Brick;
import com.example.arkanoid.client.sprites.Paddle;
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
	private Long timeElapsed;
	
	private Paddle paddle;
	private Ball ball;
	private Set<Brick> brickSet;
	
	private Timer timer;
	
	public GameThread(Canvas canvas) {
		addFocusHandlers(canvas);
		context = canvas.getContext2d();
	}
	
	public Paddle getPaddle() {return paddle;}
	
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
	
	private void uptadeTimeElapsed() {
		timeElapsed = timeElapsed + Configuration.UPDATE_RATE;
		GameUtil.updateTimeDisplay(timeElapsed);
		if(timeElapsed > Configuration.LEVEL_TIMEOUT) {
			gameOver();
		}
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
	            if(getGameState() == GameState.PAUSED)
	            	setGameState(GameState.RUNNING);
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
		
		//glowna petla gry 
		timer = new Timer() {
		      @Override
		      public void run() {
		    	  if(getGameState().equals(GameState.RUNNING)) {
		    		    if(brickSet.size() == 0) win();
						if(!ball.checkIfIsInsideCanvas()) loseLife();
			    		uptadeTimeElapsed();
						GameUtil.checkAndProcessBallCollisions(ball, brickSet, paddle);
						GameUtil.clearCanvas(context);
						GameUtil.updateAndDrawSprites(context, paddle, ball, brickSet);
		    	  }
		      }
		};
		timer.scheduleRepeating(Configuration.UPDATE_RATE);
		Configuration.log(Level.INFO, "Game started");
	}
}
