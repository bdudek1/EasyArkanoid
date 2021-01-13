package com.example.arkanoid.client.sprites;

import java.util.logging.Level;

import com.example.arkanoid.client.config.Configuration;
import com.example.arkanoid.client.util.SoundBoard;
import com.google.gwt.user.client.ui.Image;

public final class Ball extends MoveableSprite {
	private boolean isHidden = false;

	public Ball(Image bitmap, double xPosition, double yPosition, double xVelocity, double yVelocity) {
		super(bitmap, xPosition, yPosition, xVelocity, yVelocity);
	}
	
	public boolean checkIfIsInsideCanvas() {
		//odbijanie pilki od scianek
		if(getX() < 0 || getX() > Configuration.CANVAS_WIDTH-Configuration.BALL_DIAMETER) {
			SoundBoard.playWallCollisionSound();
			collideHorizontally(); 
		}
		if(getY() < 0 && !isHidden) {
			SoundBoard.playWallCollisionSound();
			collideVertically(); 
		}
		
		//pilka spadla ponizej paletki
		if(getY() > Configuration.CANVAS_HEIGHT) {
			return false;
		}; 
		return true;
		
	}
	
	public boolean doesCollideWith(Sprite sprite) {
		//obliczanie krawedzi pilki
		double leftEdge = getX();
		double rightEdge = getX() + getWidth();
		double topEdge = getY();
		double bottomEdge = getY() + getHeight();
		
		//obliczanie krawedzi sprita, z ktorym koliduje lub nie koliduje pilka
		double spriteLeftEdge = sprite.getX();
		double spriteRightEdge = sprite.getX() + sprite.getWidth();
		double spriteTopEdge = sprite.getY();
		double spriteBottomEdge = sprite.getY() + sprite.getHeight();
		
		//sprawdzanie, czy kolizja nastepuje
		if( bottomEdge < spriteTopEdge ||
			topEdge > spriteBottomEdge ||
			rightEdge < spriteLeftEdge ||
			leftEdge > spriteRightEdge)
			return false;
		
		if(sprite instanceof Paddle) {
			//reakcja na kolizje pilki z paletka
			collideWithPaddle((Paddle)sprite);
		}else {
			//reakcja kolizji pilki z cegla
			SoundBoard.playBrickCollisionSound();
			if(topEdge > spriteBottomEdge-10 || bottomEdge < spriteTopEdge+10) {
				collideVertically();
			}
			else if(leftEdge > spriteRightEdge-10 || rightEdge < spriteLeftEdge+10) {
				collideHorizontally();
			}
			Configuration.log(Level.INFO, "Ball [" + getX() + ", " + getY() + "] collided with brick [" +
					  sprite.getX() + ", " + sprite.getY() +"]");
		}
		return true;

	}
	
	private void collideVertically() {
		setYVelocity(-getYVelocity());
	}
	
	private void collideHorizontally() {
		setXVelocity(-getXVelocity());
	}
	
	private void collideWithPaddle(Paddle paddle) {
		collideVertically();
		setXVelocity(-getYVelocity()*calculateBounceXVelocityModifier(paddle));
		SoundBoard.playPaddleCollisionSound();
		Configuration.log(Level.INFO, "Ball [" + getX() + ", " + getY() + "] collided with paddle [" +
						  paddle.getX() + ", " + paddle.getY() +"]");
	}
	
	//metoda potrzebna, by w glownym watku po spadnieciu pilki tylko raz odjeto zycie
	//podczas czekania na kolejna pilke
	public void hide() {
		isHidden = true;
		setY(-50);
		setXVelocity(0);
		setYVelocity(0);
	}
	
	//obliczanie wartosci, przez ktora nalezy przemnozyc predkosc x-owa pilki
	//by odbijala sie pod katem od 0 do 60 stopni
	private double calculateBounceXVelocityModifier(Paddle paddle) {
		double halfOfPaddleWidth = Configuration.PADDLE_WIDTH/2;
		double xDistanceToPaddle = getX() + Configuration.BALL_DIAMETER/2 -
							 	   (paddle.getX() + Configuration.PADDLE_WIDTH/2);
		double sin60Value = Math.sqrt(3)/2;
		return xDistanceToPaddle/halfOfPaddleWidth*sin60Value;
	}
}
