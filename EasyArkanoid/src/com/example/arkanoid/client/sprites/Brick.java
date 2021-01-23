package com.example.arkanoid.client.sprites;

import java.util.Set;

import com.example.arkanoid.client.config.Configuration;
import com.google.gwt.user.client.ui.Image;

public final class Brick extends Sprite {
	private byte durability;
	
	public Brick(Image bitmap, double xPosition, double yPosition) {
		this(bitmap, xPosition, yPosition, (byte)1);
	}
	
	public Brick(Image bitmap, double xPosition, double yPosition, byte durability) {
		super(bitmap, xPosition, yPosition);
		if(durability > 0 &&  durability < 4) {
			this.durability = durability;
		}else{
			throw new IllegalArgumentException("Wytrzymalosc cegielki musi byc " + 
												 "wieksza od 0 i mniejsza od 4!");
		}
	}
	
	public void getHitByBall(Set<Brick> brickSet) {
		durability--;
		switch(durability) {
			case 2:
				setBitmap(Configuration.getBlueBrickBitmap());
				break;
			case 1:
				setBitmap(Configuration.getYellowBrickBitmap());
				break;
			default:
				removeBrick(brickSet);
		}
	}
	
	public void removeBrick(Set<Brick> brickSet) {
		brickSet.remove(this);
	}
	
	@Override
	public boolean equals(Object o) {
		if(getClass()!=o.getClass()) {
			return false;
		}
		
		Brick brick = (Brick)o;
		if(getX() == brick.getX() && getY() == brick.getY()) {
			return true;
		}else {
			return false;
		}
	}


}
