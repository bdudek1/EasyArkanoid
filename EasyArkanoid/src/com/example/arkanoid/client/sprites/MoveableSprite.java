package com.example.arkanoid.client.sprites;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.ui.Image;

public class MoveableSprite extends Sprite {
	private double yVelocity;
	private double xVelocity;
	
	public MoveableSprite(Image bitmap, double xPosition, double yPosition) {
		super(bitmap, xPosition, yPosition);
	}
	
	public MoveableSprite(Image bitmap, double xPosition, double yPosition,
						  double xVelocity, double yVelocity) {
		super(bitmap, xPosition, yPosition);
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
	}
	
	public void setXVelocity(double xVelocity) {this.xVelocity = xVelocity;}
	public void setYVelocity(double yVelocity) {this.yVelocity = yVelocity;}
	
	public double getXVelocity() {return xVelocity;}
	public double getYVelocity() {return yVelocity;}
	
	public void updatePosition(double velocityAdjustment) {
		setX(getX()+xVelocity*velocityAdjustment);
		setY(getY()+yVelocity*velocityAdjustment);
	}
	
	public void updatePositionAndDraw(Context2d context, String canvasId, double velocityAdjustment) {
		updatePosition(velocityAdjustment);
		draw(context, canvasId);
	}
	

}
