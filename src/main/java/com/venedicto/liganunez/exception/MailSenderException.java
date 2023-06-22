package com.venedicto.liganunez.exception;

public class MailSenderException extends RuntimeException {
	private static final long serialVersionUID = 9152993931321291166L;
	
	public MailSenderException(String message) {
		super(message);
	}
}
