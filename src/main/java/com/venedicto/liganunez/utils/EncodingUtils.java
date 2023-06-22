package com.venedicto.liganunez.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncodingUtils {
	private PasswordEncoder bCryptEncoder;
	
	public EncodingUtils() {
		bCryptEncoder = new BCryptPasswordEncoder();
	}
	
	public String encode(String value) {
		return bCryptEncoder.encode(value);
	}
}
