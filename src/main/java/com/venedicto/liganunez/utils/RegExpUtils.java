package com.venedicto.liganunez.utils;

import java.util.regex.Pattern;

public class RegExpUtils {
	private final static String EMAIL_REGEXP = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
	
	public static boolean isValidEmail(String value) {
		return Pattern.compile(EMAIL_REGEXP).matcher(value).matches();
	}
}