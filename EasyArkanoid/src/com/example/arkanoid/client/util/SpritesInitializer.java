package com.example.arkanoid.client.util;

import java.util.Set;
import java.util.HashSet;

import com.example.arkanoid.client.config.Configuration;
import com.example.arkanoid.client.sprites.Ball;
import com.example.arkanoid.client.sprites.Brick;
import com.example.arkanoid.client.sprites.Paddle;

public final class SpritesInitializer {
	
	public static Paddle createPaddle(int xPosition, int yPosition) {
		return new Paddle(Configuration.getPaddleBitmap(), xPosition, yPosition);
	}

	public static Ball getBall(int xPosition, int yPosition, double xVelocity, double yVelocity) {
		return new Ball(Configuration.getBallBitmap(), xPosition, yPosition, xVelocity, yVelocity);
	}
	
	public static Ball createBall() {
		return getBall(Configuration.CANVAS_WIDTH/2, Configuration.CANVAS_HEIGHT/2,
			 	Configuration.BASE_BALL_SPEED*Configuration.getBallSpeedAdjustment(),
			 	Configuration.BASE_BALL_SPEED*Configuration.getBallSpeedAdjustment());
	}
	
	//inicjalizacja ulozenia cegiel, rowsNumber to ilosc "wierszy", a
	//columnsNumber to ilosc kolumn
	public static Set<Brick> createBricks(byte rowsNumber, byte columnsNumber){
		//startowe koordynaty polozenia cegiel
		int startXPosition = (Configuration.CANVAS_WIDTH - 
							 columnsNumber*Configuration.getBrickWidth())/2;
		int startYPosition = Configuration.getBrickHeight()*2;
		Set<Brick> brickSet = new HashSet<Brick>();
		byte durability;
		for (byte row = 0; row < rowsNumber; row++) {
			for (byte column = 0; column < columnsNumber; column++) {
					switch(row) {
						case 0:
							durability = 3;
							break;
						case 1:
							durability = 2;
							break;
						default:
							durability = 1;
					}
					brickSet.add(BrickFactory.getBrick(row, column, durability,
						  							startXPosition, startYPosition));
			}
		}
		return brickSet;
	}
}
