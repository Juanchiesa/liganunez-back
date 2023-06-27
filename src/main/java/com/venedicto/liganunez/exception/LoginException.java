package com.venedicto.liganunez.exception;

import lombok.Getter;

public class LoginException extends RuntimeException {
	private static final long serialVersionUID = 436653801793364871L;
	@Getter
	private String message;
	
	public LoginException(String message) {
		super(message);
	}
}
