package com.example.arkanoid.client.util;

import java.util.logging.Level;

import com.example.arkanoid.client.config.Configuration;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.Timer;

//klasa jest idealnym kandydatem na bycie klasa typu Aspect
//z metodami Advice, ale z powodu wymogu uzycia jedynie Java + GWT 
//nie zalaczam Springa
public final class SoundBoard {
	private static Audio paddleCollisionSound;
	private static Audio brickCollisionSound;
	private static Audio wallCollisionSound;
	private static Audio startSound;
	private static Audio loseSound;
	private static Audio loseLifeSound;
	private static Audio winSound;
	
	private static boolean isSoundEnabled;
	
	public static void loadSounds(){
		paddleCollisionSound = Audio.createIfSupported();
		paddleCollisionSound.setSrc(Configuration.FILES_PATH + "sounds/paddle_collision.flac");
			
		startSound = Audio.createIfSupported();
		startSound.setSrc(Configuration.FILES_PATH + "sounds/start.mp3");
			
		loseSound = Audio.createIfSupported();
		loseSound.setSrc(Configuration.FILES_PATH + "sounds/lose.wav");
			
		loseLifeSound = Audio.createIfSupported();
		loseLifeSound.setSrc(Configuration.FILES_PATH + "sounds/lose_life.wav");
			
		winSound = Audio.createIfSupported();
		winSound.setSrc(Configuration.FILES_PATH + "sounds/win.wav");
		Timer t = new Timer() {
			byte i = 0;
		    @Override
		    public void run() {
		    	if(winSound == null && i < 10) {
		    		i++;
		    	}else if(winSound != null) {
			        Configuration.log(Level.INFO, "Zaladowano dzwieki.");
			        isSoundEnabled = true;
			        cancel();
		    	}else if(i > 9) {
			    	Configuration.log(Level.WARNING, "Nie udalo sie zaladowac dzwiekow.");
			        isSoundEnabled = false;
			        cancel();
			        throw new ResourcesLoadingException("Nie udalo sie zaladowac dzwiekow.");
		    	}
		    }
		}; 
		t.scheduleRepeating(100);
	}
	
	public static void playPaddleCollisionSound() {
		if(isSoundEnabled) {
			paddleCollisionSound.play();
		}
	}
	
	//tworzone sa nowe obiekty tego dzwieku, by kilka kolizji w krotkim czasie 
	//byly odpowiednio reprezentowane dzwiekowo
	public static void playBrickCollisionSound() {
		if(isSoundEnabled) {
			brickCollisionSound = Audio.createIfSupported();
			brickCollisionSound.setSrc(Configuration.FILES_PATH + "sounds/brick_collision.wav");
			brickCollisionSound.play();
		}
	}
	
	//jak wyzej
	public static void playWallCollisionSound() {
		if(isSoundEnabled) {
			wallCollisionSound = Audio.createIfSupported();
			wallCollisionSound.setSrc(Configuration.FILES_PATH + "sounds/wall_collision.wav");
			wallCollisionSound.play();
		}
	}
	
	public static void playStartSound() {
		if(isSoundEnabled) {
			startSound.play();
		}
	}
	
	public static void playLoseSound() {
		if(isSoundEnabled) {
			loseSound.play();
		}
	}
	
	public static void playWinSound() {
		if(isSoundEnabled) {
			winSound.play();
		}
	}
	
	public static void playLoseLifeSound() {
		if(isSoundEnabled) {
			loseLifeSound.play();
		}
	}
}
