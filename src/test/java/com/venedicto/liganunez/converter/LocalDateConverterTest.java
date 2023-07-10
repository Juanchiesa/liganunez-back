package com.venedicto.liganunez.converter;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.LocalDate;

import com.venedicto.liganunez.utils.converter.LocalDateConverter;

public class LocalDateConverterTest {
	private LocalDateConverter converter;
	
	@Before
	public void setUp() {
		converter = new LocalDateConverter("yyyy-MM-dd");
	}
	
	@Test
	public void stringToDate_ok() {
		LocalDate response = converter.convert("2023-07-10");
		
		assertEquals(10, response.getDayOfMonth());
		assertEquals(7, response.getMonthValue());
		assertEquals(2023, response.getYear());
	}
	
	@Test
	public void stringToDate_null() {
		LocalDate response = converter.convert(null);
		assertNull(response);
	}
	
	@Test
	public void stringToDate_empty() {
		LocalDate response = converter.convert("");
		assertNull(response);
	}
}