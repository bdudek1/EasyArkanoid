package com.example.arkanoid.client.config;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.arkanoid.client.util.GameThread;
import com.example.arkanoid.client.util.ResourcesLoadingException;
import com.example.arkanoid.client.util.input.InputProcessor;
import com.example.arkanoid.client.util.input.KeyEvent;
import com.example.arkanoid.client.util.input.KeyType;
import com.example.arkanoid.client.util.input.KeyboardInput;
import com.example.arkanoid.client.util.input.MouseInput;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public final class Configuration {
	
	private static final Logger LOGGER = Logger.getLogger("");
	
	private static Image paddleBitmap;
	private static Image ballBitmap;
	private static Image blueBrickBitmap;
	private static Image redBrickBitmap;
	private static Image yellowBrickBitmap;
	
	private static int ballDiameter;
	private static int paddleHeight;
	private static int paddleWidth;
	private static int brickHeight;
	private static int brickWidth;
	
	public static final double BASE_BALL_SPEED = 1.5;
	public static final double BASE_PADDLE_SPEED = 5.0;
	
	//5min na rozegranie poziomu, czas w [ms]
	public static final int LEVEL_TIMEOUT = 300000;
	
	//czas jednego tiku wynikajacy z natury funkcji
	//AnimationScheduler.get().requestAnimationFrame(this), ktora wywoluje rekurencyjnie
	//callback w takej samej czestotliwosci, jaka czestotliwosc ma uzywany monitor w [Hz],
	//moj monitor ma 120Hz, ale inne maja np. 60Hz, 144Hz, szukalem caly dzien mozliwosci pobrania
	//wartosci czestotliwosci monitora ale, ze GWT nie moze zaimportowac Javowskiej biblioteki
	//java.awt.*, nie znalazlem
	//wartosc jest ustawiona na 1000/120[Hz], poniewaz jednostka Hz jest 1/s, wiec kiedy
	//wartosc bezjednostkowa 1000 podzielimy przez 1/s, otrzymamy wartosc w sekundach, a mnozymy
	//razy 1000, by otrzymac milisekundy
	public static final int MS_PER_UPDATE = 1000/120; //[ms]
	
	public static final String CANVAS_HOLDER_ID = "canvas";
	public static final int CANVAS_WIDTH = 400;
	public static final int CANVAS_HEIGHT = 600;
	public static final String CANVAS_NOT_SUPPORTED_WARNING =
			"Your browser does not support HTML5 Canvas";
	public static final String FILES_PATH = GWT.getModuleBaseForStaticFiles();
	
	private static int level = 1;
	private static double ballSpeedAdjustment = 1;
	private static byte brickRowsNumber;
	private static byte brickColumnsNumber;
	
	//ustalanie poziomu trudnosci gry, co za tym idzie predkosci pilki
	public static void setLevel(int setLevel) {
		if(level > 0 && level < 6) {
			level = setLevel;
			ballSpeedAdjustment = 1 + (level-1)*0.15;
		}else {
			throw new IllegalArgumentException("Jest jedynie piec poziomow trudnosci - od 1 do 5.");
		}
	}
	
	public static double getBallSpeedAdjustment() {
		return ballSpeedAdjustment;
	}
	
	public static byte getBrickRowsNumber() {
		return brickRowsNumber;
	}
	
	public static byte getBrickColumnsNumber() {
		return brickColumnsNumber;
	}
	
	public static Image getPaddleBitmap() {
		return paddleBitmap;
	}
	
	public static Image getBallBitmap() {
		return ballBitmap;
	}
	
	public static Image getBlueBrickBitmap() {
		return blueBrickBitmap;
	}
	
	public static Image getYellowBrickBitmap() {
		return yellowBrickBitmap;
	}
	
	public static Image getRedBrickBitmap() {
		return redBrickBitmap;
	}
	
	public static int getBallDiameter() {
		return ballDiameter;
	}
	
	public static int getPaddleWidth() {
		return paddleWidth;
	}
	
	public static int getPaddleHeight() {
		return paddleHeight;
	}
	
	public static int getBrickWidth() {
		return brickWidth;
	}
	
	public static int getBrickHeight() {
		return brickHeight;
	}
	
	//ustalanie liczby wierszy cegiel, musi byc wieksza od 0 i mniejsza od 9
	public static void setBrickRowsNumber(byte brickRows) {
		if(brickRows > 0 && brickRows < 9) {
				brickRowsNumber = brickRows;
		}else {
			log(Level.SEVERE, "Nieprawidlowa liczba wierszy cegiel!");
			throw new IllegalArgumentException("Nieprawidlowa liczba wierszy cegiel!");
		}
	}
	
	//ustalanie liczby kolumn cegiel, musi byc wieksza od 0 i mniejsza od 9
	public static void setBrickColumnsNumber(byte brickColumns) {
		if(brickColumns > 0 && brickColumns < 9) {
			brickColumnsNumber = brickColumns;
		}else {
			log(Level.SEVERE, "Nieprawidlowa liczba kolumn cegiel!");
			throw new IllegalArgumentException("Nieprawidlowa liczba kolumn cegiel!");
		}
	}
		
	
	
	 public static void configureCanvasAndControls(Canvas canvas, GameThread game){
		 //ustalenie wymiarow canvas
	     canvas.setWidth(Configuration.CANVAS_WIDTH + "px");
	     canvas.setHeight(Configuration.CANVAS_HEIGHT + "px");
	     canvas.setCoordinateSpaceWidth(Configuration.CANVAS_WIDTH);
	     canvas.setCoordinateSpaceHeight(Configuration.CANVAS_HEIGHT);
	     
	     //ustalenie kontroli inputow - konfiguracja sterowania
			canvas.addKeyDownHandler(new KeyDownHandler(){
			    @Override
			    public void onKeyDown(KeyDownEvent event) {
			    	if(event.isRightArrow()) {
			    		InputProcessor.addInputToQueue(
			    				new KeyboardInput(KeyType.ARROW_RIGHT, KeyEvent.DOWN,
			    								  System.currentTimeMillis()));
			    	}else if(event.isLeftArrow()) {
			    		InputProcessor.addInputToQueue(
								new KeyboardInput(KeyType.ARROW_LEFT, KeyEvent.DOWN,
												  System.currentTimeMillis()));
			    	}
			    }
			});
			canvas.addKeyUpHandler(new KeyUpHandler() {
				@Override
				public void onKeyUp(KeyUpEvent event) {
					if(event.isRightArrow()) {
						InputProcessor.addInputToQueue(
							new KeyboardInput(KeyType.ARROW_RIGHT, KeyEvent.UP,
											  System.currentTimeMillis()));
					}else if(event.isLeftArrow()) {
						InputProcessor.addInputToQueue(
							new KeyboardInput(KeyType.ARROW_LEFT, KeyEvent.UP,
											  System.currentTimeMillis()));
					}
				}
			});
			canvas.addMouseDownHandler(new MouseDownHandler() {
				@Override
				public void onMouseDown(MouseDownEvent event) {
					InputProcessor.addInputToQueue(
						new MouseInput(KeyEvent.DOWN, System.currentTimeMillis(), event.getX()));
				}
			});
			canvas.addMouseUpHandler(new MouseUpHandler(){
				@Override
				public void onMouseUp(MouseUpEvent event) {
					InputProcessor.addInputToQueue(
						new MouseInput(KeyEvent.UP, System.currentTimeMillis()));
				}
			});
			canvas.addMouseMoveHandler(new MouseMoveHandler() {
				@Override
				public void onMouseMove(MouseMoveEvent event) {
					InputProcessor.addInputToQueue(
						new MouseInput(KeyEvent.MOVE, System.currentTimeMillis(), event.getX()));
				}
			});
			RootPanel.get(Configuration.CANVAS_HOLDER_ID).add(canvas);
	 }
	 
	 public static void log(Level level, String string){
		 LOGGER.log(level, string);
	 }
	 
	 //jako, ze GWT nie wspiera wielowatkowosci Javy, w tym CompletableFuture<T> nie znalazlem
	 //lepszego sposobu na zaladowanie i pobranie wartosci wymiarow bitmap, funkcja co 100ms
	 //sprawdza, czy udalo sie zaladowac bitmapy, ktorych ladowanie rozpoczyna sie na poczatku
	 //funkcji, przewidziane jest 10 prob, po czym funkcja uznaje, ze nie udalo sie zaladowac
	 //bitmap, sytuacja jest identyczna w analogicznej funkcji w klasie SoundBoard przy
	 //ladowaniu dzwiekow
	 public static void loadBitmaps() {
      	paddleBitmap = new Image(FILES_PATH + "bitmaps/paddle.bmp");
      	ballBitmap = new Image(FILES_PATH + "bitmaps/ball.bmp");
      	blueBrickBitmap = new Image(FILES_PATH + "bitmaps/brickblue.bmp");
      	redBrickBitmap = new Image(FILES_PATH + "bitmaps/brickred.bmp");
      	yellowBrickBitmap = new Image(FILES_PATH + "bitmaps/brickyellow.bmp");
		Timer t = new Timer() {
			byte i = 0;
		    @Override
		    public void run() {
		    	if(yellowBrickBitmap == null && i < 10) {
		    		i++;
		    	}else if(yellowBrickBitmap != null) {
		        	ballDiameter = ballBitmap.getHeight();
		        	paddleHeight = paddleBitmap.getHeight();
		        	paddleWidth = paddleBitmap.getWidth();
		        	brickHeight = yellowBrickBitmap.getHeight();
		        	brickWidth = yellowBrickBitmap.getWidth();
		        	log(Level.INFO, "Zaladowano bitmapy.");
		        	cancel();
		    	}else if(i > 9) {
		        	log(Level.SEVERE, "Nie udalo sie zaladowac bitmap.");
		        	cancel();
		        	throw new ResourcesLoadingException("Nie udalo sie zaladowac bitmap.");
		    	}
		    }
		}; 
		t.scheduleRepeating(100);
	 }
}
