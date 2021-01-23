package com.example.arkanoid.client.sprites;

import com.google.gwt.user.client.ui.Image;
import com.example.arkanoid.client.config.Configuration;

public final class Paddle extends MoveableSprite {

	public Paddle(Image bitmap, double xPosition, double yPosition) {
		super(bitmap, xPosition, yPosition);
	}
	
	@Override
	public void updatePosition(double velocityAdjustment) {
		//sprawdza, czy paletka bedzie wychodzic wychodzi poza ekran,
		//jesli nie, aktualizuje pozycje
		if((getXvelocity() > 0 && getX() <
			Configuration.CANVAS_WIDTH - Configuration.getPaddleWidth()) ||
		   (getXvelocity() < 0 && getX() > 0)) {
				setX(getX()+getXvelocity()*velocityAdjustment);
		}
	}
}
