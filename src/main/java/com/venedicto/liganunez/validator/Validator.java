package com.venedicto.liganunez.validator;

public class Validator {
	protected boolean stringNullOrEmpty(String value) {
		return value == null || value.isEmpty();
	}
}