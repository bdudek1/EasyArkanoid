package com.example.arkanoid.client.util;

public class ResourcesLoadingException extends RuntimeException {
	public ResourcesLoadingException() {
		this("Failed to load resources");
	}
	public ResourcesLoadingException(String message) {
		super(message);
	}
}
