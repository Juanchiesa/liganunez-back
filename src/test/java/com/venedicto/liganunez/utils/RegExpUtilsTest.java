package com.venedicto.liganunez.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RegExpUtilsTest {
	/** Emails **/
	@Test
	public void testValidEmails() {
		assertTrue(RegExpUtils.isValidEmail("user@domain.com"));
		assertTrue(RegExpUtils.isValidEmail("user.name@domain.com"));
		assertTrue(RegExpUtils.isValidEmail("user@domain.co.in"));
		assertTrue(RegExpUtils.isValidEmail("username@yahoo.corporate.in"));
		assertTrue(RegExpUtils.isValidEmail("user_name@domain.com"));
	}
	@Test
	public void testInvalidEmails() {
		assertFalse(RegExpUtils.isValidEmail(".username@yahoo.com"));
		assertFalse(RegExpUtils.isValidEmail("username@yahoo.com."));
		assertFalse(RegExpUtils.isValidEmail("username@yahoo..com"));
		assertFalse(RegExpUtils.isValidEmail("username@yahoo.c"));
		assertFalse(RegExpUtils.isValidEmail("username@yahoo.corporate"));
	}
}
