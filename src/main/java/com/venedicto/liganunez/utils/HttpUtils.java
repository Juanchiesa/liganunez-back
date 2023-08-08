package com.venedicto.liganunez.utils;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.venedicto.liganunez.model.ErrorCodes;
import com.venedicto.liganunez.model.http.Error;
import com.venedicto.liganunez.model.http.HttpResponse;

public class HttpUtils {
	public static Error generateError(ErrorCodes errorCode) {
		Error error = new Error();
		error.setCode("["+errorCode.getId()+"]"+errorCode.getCode());
		error.setDescription(errorCode.getDescription());
		return error;
	}
	
	public static <T> ResponseEntity<T> badRequestResponse(Logger log, T response, List<Error> errors) {
		log.error("[Check user] Request errónea: se han encontrado {} errores", errors.size());
		((HttpResponse) response).setOpCode("400");
		((HttpResponse) response).setErrors(errors);
		return new ResponseEntity<T>(response, HttpStatus.BAD_REQUEST);
	}
}