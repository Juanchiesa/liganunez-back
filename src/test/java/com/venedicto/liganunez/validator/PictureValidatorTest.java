package com.venedicto.liganunez.validator;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.venedicto.liganunez.model.http.Error;

public class PictureValidatorTest {
	private PictureValidator validator;
	
	@Before
	public void setUp() {
		validator = new PictureValidator();
	}
	
	@Test
	public void fileUpload_validUpload() {
		List<Error> response = validator.validateFileUpload("xxxxxx-xx-xx-x", "Test address", "2023-07-10");
		assertEquals(0, response.size());
	}
	
	@Test
	public void fileUpload_invalidDate() {
		List<Error> response = validator.validateFileUpload("xxxxxx-xx-xx-x", "Test address", "10/07/2023");
		assertEquals(1, response.size());
	}
	
	@Test
	public void fileUpload_invalidUpload() {
		List<Error> response = validator.validateFileUpload("", "", "");
		assertEquals(3, response.size());
	}
}
