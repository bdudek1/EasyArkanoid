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
	private static final Label livesLeftLabel = new Label();
	private static final Label timeLeftLabel = new Label();
	private static final Label gameOverLabel = new Label("Game over! Click Start to play again.");
	private static final Label wonLabel = new Label("You win! Click Start game to play again.");
	
	//wykrywanie i obsluga kolizji pilki
	public static void checkAndProcessBallCollisions(Ball ball, Set<Brick> brickSet, Paddle paddle) {
		ball.doesCollideWith(paddle);
		Optional<Brick> brick = brickSet.stream().filter(ball::doesCollideWith).findFirst();
		brick.ifPresent(b -> b.getHitByBall(brickSet));
	}
	
	public static void gameOver() {
		UICreator.showMenu();
		SoundBoard.playLoseSound();
		Configuration.log(Level.INFO, "Game over");
		RootPanel.get(Configuration.CANVAS_HOLDER_ID).get("gameover").add(gameOverLabel);
	}
	
	public static void win() {
		UICreator.showMenu();
		SoundBoard.playWinSound();
		Configuration.log(Level.INFO, "Game won");
		RootPanel.get(Configuration.CANVAS_HOLDER_ID).get("gameover").add(wonLabel);
	}
	
	public static void updateSpritesPosition(Paddle paddle, Ball ball) {
		ball.updatePosition(Configuration.getBallSpeedAdjustment());
		paddle.updatePosition(1);
	}
	
	public static void drawSprites(Context2d context, Paddle paddle,
									Ball ball, Set<Brick> brickSet,
									double positionExtrapolationAdjustment) {
		ball.draw(context, Configuration.CANVAS_HOLDER_ID, positionExtrapolationAdjustment);
		paddle.draw(context, Configuration.CANVAS_HOLDER_ID, positionExtrapolationAdjustment);
		brickSet.forEach(a -> a.draw(context, Configuration.CANVAS_HOLDER_ID));
	}
	
	public static void updateLivesDisplay(byte lives) {
		livesLeftLabel.setText("Lives left = " + lives);
		RootPanel.get(Configuration.CANVAS_HOLDER_ID).get("lives").add(livesLeftLabel);
	}
	
	public static void updateTimeDisplay(double timeElapsed) {
		timeLeftLabel.setText("Time left = " + (GameUtil.convertMilisecondsToSeconds(
								Configuration.LEVEL_TIMEOUT-timeElapsed)) + " s");
		RootPanel.get(Configuration.CANVAS_HOLDER_ID).get("time").add(timeLeftLabel);
	}
	
	//czyszczenie obrazu, by narysowac sprite'y z zaktualizowana pozycja
	//w glownej petli gry
	public static void clearCanvas(Context2d context) {
		context.clearRect(0, 0, Configuration.CANVAS_WIDTH, Configuration.CANVAS_HEIGHT);
	}
	
	public static int convertMilisecondsToSeconds(double d) {
		return (int)d/1000;
	}
}
