package com.example.arkanoid.client.util;

import java.util.logging.Level;

import com.example.arkanoid.client.config.Configuration;
import com.google.gwt.media.client.Audio;

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
	
	public static void initializeSounds() {
		try {
			paddleCollisionSound = Audio.createIfSupported();
			paddleCollisionSound.setSrc("sounds/paddle_collision.flac");
			
			startSound = Audio.createIfSupported();
			startSound.setSrc("sounds/start.mp3");
			
			loseSound = Audio.createIfSupported();
			loseSound.setSrc("sounds/lose.wav");
			
			loseLifeSound = Audio.createIfSupported();
			loseLifeSound.setSrc("sounds/lose_life.wav");
			
			winSound = Audio.createIfSupported();
			winSound.setSrc("sounds/win.wav");
			
		  	Configuration.SOUND_ENABLED = true;
		}catch(NullPointerException e) {
		    Configuration.SOUND_ENABLED = false;
		    Configuration.log(Level.WARNING, "Sound not supported");
		    e.printStackTrace();
		}
	}
	
	public static void playPaddleCollisionSound() {
		if(Configuration.SOUND_ENABLED)
		paddleCollisionSound.play();
	}
	
	//tworzone sa nowe obiekty tego dzwieku, by kilka kolizji w krotkim czasie 
	//byly odpowiednio reprezentowane dzwiekowo
	public static void playBrickCollisionSound() {
		if(Configuration.SOUND_ENABLED) {
			brickCollisionSound = Audio.createIfSupported();
			brickCollisionSound.setSrc("sounds/brick_collision.wav");
			brickCollisionSound.play();
		}
	}
	
	//jak wyzej
	public static void playWallCollisionSound() {
		if(Configuration.SOUND_ENABLED) {
			wallCollisionSound = Audio.createIfSupported();
			wallCollisionSound.setSrc("sounds/wall_collision.wav");
			wallCollisionSound.play();
		}
	}
	
	public static void playStartSound() {
		if(Configuration.SOUND_ENABLED)
		startSound.play();
	}
	
	public static void playLoseSound() {
		if(Configuration.SOUND_ENABLED)
		loseSound.play();
	}
	
	public static void playWinSound() {
		if(Configuration.SOUND_ENABLED)
		winSound.play();
	}
	
	public static void playLoseLifeSound() {
		if(Configuration.SOUND_ENABLED)
		loseLifeSound.play();
	}
}
