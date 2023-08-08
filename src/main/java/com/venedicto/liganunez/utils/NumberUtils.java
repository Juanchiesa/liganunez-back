package com.venedicto.liganunez.utils;

import java.util.Random;

public class NumberUtils {
	public static String generateRandomNumber(int maxChars) {
		String chars = "";
		for(int i=0; i<maxChars; i++) {
			chars += "9";
		}
		
		int number = new Random().nextInt(Integer.valueOf(chars));
		String formattedNumber = String.format("%0"+maxChars+"d", number);
		
		return formattedNumber;
	}
}
