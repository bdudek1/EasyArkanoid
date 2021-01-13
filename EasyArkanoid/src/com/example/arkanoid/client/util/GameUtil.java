package com.example.arkanoid.client.util;

import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;

import com.example.arkanoid.client.config.Configuration;
import com.example.arkanoid.client.config.UICreator;
import com.example.arkanoid.client.sprites.Ball;
import com.example.arkanoid.client.sprites.Brick;
import com.example.arkanoid.client.sprites.Paddle;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public final class GameUtil {
	
	//wykrywanie i obsluga kolizji pilki
	public static void checkAndProcessBallCollisions(Ball ball, Set<Brick> brickSet, Paddle paddle) {
		ball.doesCollideWith(paddle);
		Optional<Brick> brick = brickSet.stream()
										.filter(a -> ball.doesCollideWith(a))
										.findFirst();
		brick.ifPresent(b -> b.getHitByBall(brickSet));
	}
	
	public static void gameOver() {
		UICreator.showMenu();
		SoundBoard.playLoseSound();
		Configuration.log(Level.INFO, "Game over");
		RootPanel.get(Configuration.CANVAS_HOLDER_ID).get("gameover").add(
				new Label("Game over! Click Start game to play again."));
	}
	
	public static void win() {
		UICreator.showMenu();
		SoundBoard.playWinSound();
		Configuration.log(Level.INFO, "Game won");
		RootPanel.get(Configuration.CANVAS_HOLDER_ID).get("gameover").add(
				new Label("You win! Click Start game to play again."));
	}
	
	public static void updateAndDrawSprites(Context2d context, Paddle paddle,
											Ball ball, Set<Brick> brickSet) {
		//tlo jest ustalone w pliku EasyArkanoid.css w klasie .canvas
		ball.updatePositionAndDraw(context,
				Configuration.CANVAS_HOLDER_ID,
				Configuration.getBallSpeedAdjustment());
		
		paddle.updatePositionAndDraw(context, Configuration.CANVAS_HOLDER_ID, 1);
		brickSet.forEach(a -> a.draw(context, Configuration.CANVAS_HOLDER_ID));
	}
	
	public static void updateLivesDisplay(byte lives) {
		RootPanel.get(Configuration.CANVAS_HOLDER_ID).get("lives").clear();
		RootPanel.get(Configuration.CANVAS_HOLDER_ID).get("lives").add(
				new Label("Lives left = " + lives));
	}
	
	public static void updateTimeDisplay(Long timeElapsed) {
		RootPanel.get(Configuration.CANVAS_HOLDER_ID).get("time").clear();
		RootPanel.get(Configuration.CANVAS_HOLDER_ID).get("time").add(
				new Label("Time left = " + 
						(GameUtil.convertMilisecondsToSeconds(Configuration.LEVEL_TIMEOUT-timeElapsed)) + " s"));
	}
	
	//czyszczenie obrazu, by narysowac sprite'y z zaktualizowana pozycja
	//w glownej petli gry
	public static void clearCanvas(Context2d context) {
		context.clearRect(0, 0, Configuration.CANVAS_WIDTH, Configuration.CANVAS_HEIGHT);
	}
	
	public static long convertMilisecondsToSeconds(long miliSeconds) {
		return miliSeconds/1000;
	}
}
