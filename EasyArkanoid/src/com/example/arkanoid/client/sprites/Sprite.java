package com.example.arkanoid.client.sprites;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

public abstract class Sprite {
	private double xPosition;
	private double yPosition;
	private Image bitmap;
	protected ImageElement imEl;
	
	public Sprite(Image bitmap, double xPosition, double yPosition) {
		this.bitmap = bitmap;
		this.imEl = ImageElement.as(bitmap.getElement());
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}
	
	public void draw(Context2d context, String canvasId) {
    	context.drawImage(imEl, getX(), getY());
	}
	
	//Funkcja oblicza odleglosc do sprita na podstawie twierdzenia Pitagorasa
	//castowanie do int, poniewaz ^2 nie dziala na double
	public double getDistanceTo(Sprite target){
		return Math.sqrt(((int)getX() - (int)target.getX())^2 + 
						 ((int)getY() - (int)target.getY())^2);
	}
	
	public double getX() {
		return xPosition;
	}
	
	public double getY() {
		return yPosition;
	}
	
	public void setX(double xPosition) {
		this.xPosition = xPosition;
	}
	
	public void setY(double yPosition) {
		this.yPosition = yPosition;
	}
	
	public int getWidth() {
		return bitmap.getWidth();
	}
	
	public int getHeight() {
		return bitmap.getHeight();
	}
	
	public Image getBitmap() {
		return bitmap;
	}
	
	public void setBitmap(Image bitmap) {
		this.bitmap = bitmap;
		this.imEl = ImageElement.as(bitmap.getElement());
	}

	@Override
	public String toString() {
		return "Sprite path: " + bitmap.getUrl() +
				", x position: " + getX() +
				", y position: " + getY();
	}
}
