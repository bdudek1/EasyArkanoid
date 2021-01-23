package com.example.arkanoid.client.util.input;

import java.util.Date;
import java.util.logging.Level;

import com.example.arkanoid.client.config.Configuration;

public abstract class Input {
	private KeyType keyType;
	private KeyEvent keyEvent;
	private long time;
	
	public Input(KeyType keyType, KeyEvent keyEvent, long time) {
		this.keyType = keyType;
		this.keyEvent = keyEvent;
		this.time = time;
		Configuration.log(Level.INFO, "Input key " + keyType + ", event " + keyEvent +
						 " registered at " + new Date(time).toString());
	}
	
	public KeyType getKeyType() {
		return keyType;
	}
	
	public KeyEvent getKeyEvent() {
		return keyEvent;
	}
	
	public long getRegisteredTime() {
		return time;
	}
}
