package com.venedicto.liganunez.converter;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.LocalDateTime;

import com.venedicto.liganunez.utils.converter.LocalDateTimeConverter;

public class LocalDateTimeConverterTest {
	private LocalDateTimeConverter converter;
	
	@Before
	public void setUp() {
		converter = new LocalDateTimeConverter("yyyy-MM-dd HH:mm:ss");
	}
	
	@Test
	public void stringToDate_ok() {
		LocalDateTime response = converter.convert("2023-07-10 12:00:00");
		
		assertEquals(10, response.getDayOfMonth());
		assertEquals(7, response.getMonthValue());
		assertEquals(2023, response.getYear());
		assertEquals(12, response.getHour());
		assertEquals(0, response.getMinute());
		assertEquals(0, response.getSecond());
	}
	
	@Test
	public void stringToDate_null() {
		LocalDateTime response = converter.convert(null);
		assertNull(response);
	}
	
	@Test
	public void stringToDate_empty() {
		LocalDateTime response = converter.convert("");
		assertNull(response);
	}
}