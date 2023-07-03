package com.venedicto.liganunez.exception;

public class FileNotFoundExcepion extends RuntimeException {
	private static final long serialVersionUID = -8177235816953874880L;

	public FileNotFoundExcepion(String message) {
		super(message);
	}
}