package com.example.arkanoid.client.sprites;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.ui.Image;

public abstract class MoveableSprite extends Sprite {
	private double yVelocity;
	private double xVelocity;
	
	public MoveableSprite(Image bitmap, double xPosition, double yPosition) {
		this(bitmap, xPosition, yPosition, 0, 0);
	}
	
	public MoveableSprite(Image bitmap, double xPosition, double yPosition,
						  double xVelocity, double yVelocity) {
		super(bitmap, xPosition, yPosition);
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
	}
	
	public void setXvelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}
	
	public void setYvelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}
	
	public double getXvelocity() {
		return xVelocity;
	}
	
	public double getYvelocity() {
		return yVelocity;
	}
	
	public void updatePosition(double velocityAdjustment) {
		setX(getX()+xVelocity*velocityAdjustment);
		setY(getY()+yVelocity*velocityAdjustment);
	}
	
	//przeciazona funkcja draw z superklasy Sprite, by uwzglednic ekstrapolacje pozycji przy
	//sprite'ach przemieszczajacych sie
	public void draw(Context2d context, String canvasId, double positionExtrapolationAdjustment) {
    	context.drawImage(imEl, getX() + getXvelocity()*positionExtrapolationAdjustment,
    							getY() + getYvelocity()*positionExtrapolationAdjustment);
	}

}
