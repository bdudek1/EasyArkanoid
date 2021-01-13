package com.example.arkanoid;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;

import com.google.gwt.junit.client.GWTTestCase;

import java.util.Set;

import com.example.arkanoid.client.sprites.Ball;
import com.example.arkanoid.client.sprites.Brick;
import com.example.arkanoid.client.sprites.Paddle;
import com.example.arkanoid.client.util.BrickFactory;
import com.example.arkanoid.client.util.SpritesInitializer;

//z jakiegos powodu kompilator nie widzi klasy EasyArkanoidTests,
//nie zdazylem tego naprawic, dlatego wszystko jest oznaczone
//adnotacja @Disabled
public class EasyArkanoidTests extends GWTTestCase{
	private Ball ball;
	private Paddle paddle;
	private Brick brick;
	
	@Disabled
	@BeforeAll
	public void initSprites() {
		ball = SpritesInitializer.createBall();
		
		paddle = SpritesInitializer.createPaddle(100, 100);
		
		brick = BrickFactory.getBrick(1, 1, (byte)1, 1, 1);
		brick.setX(100);
		brick.setY(100);
	}
	
	@Disabled
	@RepeatedTest(50)
	public void bricksCreationTest() {
		int rowsNumber = (int)Math.random() * 7 + 1;
		int columnsNumber = (int)Math.random() * 7 + 1;
		
		Set<Brick> brickSet = SpritesInitializer.createBricks(rowsNumber, columnsNumber);
		assertEquals(rowsNumber*columnsNumber, brickSet.size());
	}
	
	@Disabled
	@RepeatedTest(50)
	public void bricksCreationShouldThrowException() {
		int rowsNumber = (int)Math.random() * 7 + 8;
		int columnsNumber = (int)Math.random() * 7 + 8;
		
		assertThrows(IllegalArgumentException.class,
					() -> SpritesInitializer.createBricks(rowsNumber, columnsNumber));
	}

	@Disabled
	@Test
	public void ballShouldCollideTest() {
		ball.setX(100);
		ball.setY(100);
		
		assertTrue(ball.doesCollideWith(paddle));
		assertTrue(ball.doesCollideWith(brick));
	}
	
	@Disabled
	@Test
	public void ballShouldNotCollideTest() {
		ball.setX(200);
		ball.setY(200);
		
		assertFalse(ball.doesCollideWith(paddle));
		assertFalse(ball.doesCollideWith(brick));
	}
	
	@Disabled
	@Test
	public void ballShouldBeInsideCanvas() {
		ball.setX(100);
		ball.setY(100);
		assertTrue(ball.checkIfIsInsideCanvas());
	}
	
	@Disabled
	@Test
	public void ballShouldNotBeInsideCanvas() {
		ball.setX(-100);
		ball.setY(100);
		assertFalse(ball.checkIfIsInsideCanvas());
		
		ball.setX(100);
		ball.setY(-100);
		assertFalse(ball.checkIfIsInsideCanvas());
		
		ball.setX(10000);
		ball.setY(100);
		assertFalse(ball.checkIfIsInsideCanvas());
		
		ball.setX(100);
		ball.setY(10000);
		assertFalse(ball.checkIfIsInsideCanvas());
	}
	
	@Override
	  public String getModuleName() {                                     
	    return "com.example.arkanoid.EasyArkanoid";
	  }
}
