package com.example.arkanoid.client.config;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.arkanoid.client.util.GameThread;
import com.google.gwt.canvas.client.Canvas;
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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public final class Configuration {
	
	private static final Logger LOGGER = Logger.getLogger("");
	
	public static final Image PADDLE_BITMAP = new Image("bitmaps/paddle.bmp");
	public static final Image BALL_BITMAP = new Image("bitmaps/ball.bmp");
	public static final Image BLUE_BRICK_BITMAP = new Image("bitmaps/brickblue.bmp");
	public static final Image RED_BRICK_BITMAP = new Image("bitmaps/brickred.bmp");
	public static final Image YELLOW_BRICK_BITMAP = new Image("bitmaps/brickyellow.bmp");
	
	//z nieznanego mi powodu ponizsze pobieranie wartosci dziala mi jedynie w
	//development mode
//	public static final int BALL_DIAMETER = BALL_BITMAP.getHeight();
//	public static final int PADDLE_HEIGHT = PADDLE_BITMAP.getHeight();
//	public static final int PADDLE_WIDTH = PADDLE_BITMAP.getWidth();
//	public static final int BRICK_HEIGHT = BLUE_BRICK_BITMAP.getHeight();
//	public static final int BRICK_WIDTH = BLUE_BRICK_BITMAP.getWidth();
	
	//by gra dzialala po normalnej kompilacji musze wprowadzic wartosci
	//w ten sposob
	public static final int BALL_DIAMETER = 16;
	public static final int PADDLE_HEIGHT = 12;
	public static final int PADDLE_WIDTH = 96;
	public static final int BRICK_HEIGHT = 16;
	public static final int BRICK_WIDTH = 32;
	
	//co ile ms ma byc wykonywana glowna petla gry
	public static final int UPDATE_RATE = 16;
	
	public static final double BASE_BALL_SPEED = 2.0;
	public static final double BASE_PADDLE_SPEED = 8.0;
	
	//5min na rozegranie poziomu, czas w [ms]
	public static final int LEVEL_TIMEOUT = 300000;
	
	public static final String CANVAS_HOLDER_ID = "canvas";
	public static final int CANVAS_WIDTH = 400;
	public static final int CANVAS_HEIGHT = 600;
	public static final String CANVAS_NOT_SUPPORTED_WARNING =
			"Your browser does not support HTML5 Canvas";
	
	private static int level = 1;
	private static double ballSpeedAdjustment = 1;
	private static int BRICK_ROWS_NUMBER;
	private static int BRICK_COLUMNS_NUMBER;
	private static boolean isMouseDown = false;
	public static boolean SOUND_ENABLED = true;
	
	//ustalanie poziomu trudnosci gry, co za tym idzie predkosci pilki
	public static void setLevel(int setLevel) {
		if(level > 0 && level < 6) {
			level = setLevel;
			ballSpeedAdjustment = 1 + (level-1)*0.15;
		}else {
			throw new IllegalArgumentException("Jest jedynie piec poziomow trudnosci - od 1 do 5.");
		}
	}
	
	public static double getBallSpeedAdjustment() {return ballSpeedAdjustment;}
	
	public static int getBrickRowsNumber() {return BRICK_ROWS_NUMBER;}
	
	public static int getBrickColumnsNumber() {return BRICK_COLUMNS_NUMBER;}
	
	//ustalanie liczby wierszy cegiel, musi byc wieksza od 0 i mniejsza od 9
	public static void setBrickRowsNumber(int brickRowsNumber) {
		if(brickRowsNumber > 0 && brickRowsNumber < 9) {
					BRICK_ROWS_NUMBER = brickRowsNumber;
		}else {
			log(Level.SEVERE, "Nieprawidlowa liczba wierszy cegiel!");
			throw new IllegalArgumentException("Nieprawidlowa liczba wierszy cegiel!");
		}
	}
	
	//ustalanie liczby kolumn cegiel, musi byc wieksza od 0 i mniejsza od 9
	public static void setBrickColumnsNumber(int brickColumnsNumber) {
		if(brickColumnsNumber > 0 && brickColumnsNumber < 9) {
			BRICK_COLUMNS_NUMBER = brickColumnsNumber;
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
			    	if(event.isRightArrow()) game.getPaddle().setXVelocity(5);
			        if(event.isLeftArrow())	 game.getPaddle().setXVelocity(-5);
			    }
			});
			canvas.addKeyUpHandler(new KeyUpHandler() {
				@Override
				public void onKeyUp(KeyUpEvent event) {
					if(event.isRightArrow() || event.isLeftArrow()) {
						game.getPaddle().setXVelocity(0);
					}
				}
			});
			canvas.addMouseDownHandler(new MouseDownHandler() {
				@Override
				public void onMouseDown(MouseDownEvent event) {
					game.getPaddle().setX(event.getX());
					isMouseDown = true;
				}
			});
			canvas.addMouseUpHandler(new MouseUpHandler(){
				@Override
				public void onMouseUp(MouseUpEvent event) {
					isMouseDown = false;
				}
			});
			canvas.addMouseMoveHandler(new MouseMoveHandler() {
				@Override
				public void onMouseMove(MouseMoveEvent event) {
					if(isMouseDown && event.getX() - Configuration.PADDLE_WIDTH/2 > 0
							 	   && event.getX() + Configuration.PADDLE_WIDTH/2 < Configuration.CANVAS_WIDTH)
					game.getPaddle().setX(event.getX()-Configuration.PADDLE_WIDTH/2);
				}
			});
			RootPanel.get(Configuration.CANVAS_HOLDER_ID).add(canvas);
	 }
	 
	 public static void log(Level level, String string){
		 LOGGER.log(level, string);
	 }
}
