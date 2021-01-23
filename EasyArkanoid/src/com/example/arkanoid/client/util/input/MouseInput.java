package com.example.arkanoid.client.util.input;

public class MouseInput extends Input {
	private int x;
	
	public MouseInput(KeyEvent keyEvent, long time) {
		this(keyEvent, time, 0);
	}
	
	public MouseInput(KeyEvent keyEvent, long time, int x) {
		super(KeyType.MOUSE, keyEvent, time);
		this.x = x;
	}
	
	public int getX() {
		return x;
	}
}
