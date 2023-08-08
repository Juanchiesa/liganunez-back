package com.venedicto.liganunez.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class NumberUtilsTest {
	@Test
	public void generateRandomNumberWith3Chars() {
		String number = NumberUtils.generateRandomNumber(3);
		assertEquals(3, number.length());
	}
	
	@Test
	public void generateRandomNumberWith6Chars() {
		String number = NumberUtils.generateRandomNumber(6);
		assertEquals(6, number.length());
	}
	
	@Test
	public void generateRandomNumberWith9Chars() {
		String number = NumberUtils.generateRandomNumber(9);
		assertEquals(9, number.length());
	}
}
