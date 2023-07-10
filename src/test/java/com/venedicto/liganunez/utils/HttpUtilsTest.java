package com.venedicto.liganunez.utils;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.venedicto.liganunez.model.ErrorCodes;
import com.venedicto.liganunez.model.http.Error;
import com.venedicto.liganunez.model.http.HttpResponse;

public class HttpUtilsTest {
	@Test
	public void generateError_ok() {
		Error error = HttpUtils.generateError(ErrorCodes.LN0000);
		assertEquals("[LN]0000", error.getCode());
		assertEquals("Ocurrió un error inesperado", error.getDescription());
	}
	
	@Test
	public void badRequestResponse_ok() {
		Logger testLogger = LoggerFactory.getLogger(HttpUtilsTest.class);
		HttpResponse response = new HttpResponse();
		List<Error> errors = new ArrayList<>();
		errors.add(HttpUtils.generateError(ErrorCodes.LN0000));
		
		ResponseEntity<HttpResponse> entity = HttpUtils.badRequestResponse(testLogger, response, errors);
		
		assertEquals("400", response.getOpCode());
		assertEquals(1, response.getErrors().size());
		assertEquals("[LN]0000", response.getErrors().get(0).getCode());
		assertEquals("Ocurrió un error inesperado", response.getErrors().get(0).getDescription());
		assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());
	}
}