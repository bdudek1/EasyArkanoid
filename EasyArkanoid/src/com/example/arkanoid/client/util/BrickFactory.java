package com.example.arkanoid.client.util;

import com.example.arkanoid.client.config.Configuration;
import com.example.arkanoid.client.sprites.Brick;

public final class BrickFactory {
	
	//fabryka cegiel
	//rowNumber - w ktorym "wierszu" jest cegla, a columnNumber to kolumna
	public static Brick getBrick(int rowNumber, int columnNumber, byte durability,
								int startXPosition, int startYPosition) {
		int brickWidth = Configuration.getBrickWidth();
		int brickHeight = Configuration.getBrickHeight();
		
		switch(durability) {
		case 3:
			return new Brick(Configuration.getRedBrickBitmap(),
							 brickWidth*columnNumber + startXPosition,
							 brickHeight*rowNumber + startYPosition,
							 durability);
		case 2:
			return new Brick(Configuration.getBlueBrickBitmap(),
							 brickWidth*columnNumber + startXPosition,
							 brickHeight*rowNumber + startYPosition,
							 durability);
		case 1:
			return new Brick(Configuration.getYellowBrickBitmap(),
							 brickWidth*columnNumber + startXPosition,
							 brickHeight*rowNumber + startYPosition,
							 durability);
		default:
			throw new IllegalArgumentException("Nieprawidlowe dane wejsciowe do tworzenia cegiel.");
		}
	}
}
