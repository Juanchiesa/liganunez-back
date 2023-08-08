package com.venedicto.liganunez.exception;

public class RequestExpiredException extends RuntimeException {
	private static final long serialVersionUID = 7473169307019832402L;

	public RequestExpiredException(String msg) {
		super(msg);
	}
}