package com.venedicto.liganunez.exception;

public class NotAuthorizedException extends RuntimeException {
	private static final long serialVersionUID = -3397179851331314695L;
	
	public NotAuthorizedException(String msg) {
		super(msg);
	}
}