package com.example.arkanoid.client.util.input;

import java.util.LinkedList;
import java.util.Queue;

import com.example.arkanoid.client.config.Configuration;
import com.example.arkanoid.client.sprites.Paddle;

public class InputProcessor {
	private static Queue<Input> inputQueue = new LinkedList<>();
	private static boolean isMouseDown = false;
	private static Object inputLock = new Object();
	
	public static void addInputToQueue(Input input) {
		synchronized(inputLock) {
			inputQueue.add(input);
		}
	}
	
	public static void processInputQueue(Paddle paddle) {
		synchronized(inputLock) {
			while(!inputQueue.isEmpty()) {
				processInput(inputQueue.poll(), paddle);
			}
		}
	}
	
	private static void processInput(Input input, Paddle paddle) {
		if(input instanceof KeyboardInput) {
			processKeyboardInput((KeyboardInput)input, paddle);
		}else if(input instanceof MouseInput) {
			processMouseInput((MouseInput)input, paddle);
		}
	}
	
	private static void processKeyboardInput(KeyboardInput input, Paddle paddle) {
		switch(input.getKeyEvent()) {
			case UP:
				paddle.setXvelocity(0);
				break;
			case DOWN:
				switch(input.getKeyType()) {
					case ARROW_LEFT:
						paddle.setXvelocity(-5);
						break;
					case ARROW_RIGHT:
						paddle.setXvelocity(5);
						break;
					default:
				}
				break;
			default:
		}
	}
	
	private static void processMouseInput(MouseInput input, Paddle paddle) {
		if(input.getKeyEvent().equals(KeyEvent.DOWN) || 
		  (input.getKeyEvent().equals(KeyEvent.MOVE) && isMouseDown)) {
					if(isInputXInsideCanvas((MouseInput)input)) {
						paddle.setX(((MouseInput) input).getX()-Configuration.getPaddleWidth()/2);
						isMouseDown = true;
						}
					}else {
						isMouseDown = false;
					}
	}
	
	//funkcja bierze pod uwage rowniez to, zeby byly ignorowane inputy takie,
	//ktore sprawia, ze paletka sie "utnie" i jej czesc bedzie poza canvas
	private static boolean isInputXInsideCanvas(MouseInput input) {
		if((input.getX() - Configuration.getPaddleWidth()/2 > 0 && 
		   (input.getX() + Configuration.getPaddleWidth()/2 < Configuration.CANVAS_WIDTH))) {
			return true;
		}
		return false;
	}
}
