package com.example.arkanoid.client.sprites;

import com.google.gwt.canvas.dom.client.Context2d;
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
		if((getXVelocity() > 0 && getX() < Configuration.CANVAS_WIDTH - Configuration.PADDLE_WIDTH) ||
		   (getXVelocity() < 0 && getX() > 0))
		setX(getX()+getXVelocity()*velocityAdjustment);
	}
	
	@Override
	public void updatePositionAndDraw(Context2d context, String canvasId, double velocityAdjustment) {
		this.updatePosition(velocityAdjustment);
		draw(context, canvasId);
	}

}
