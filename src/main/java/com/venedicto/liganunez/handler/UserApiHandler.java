package com.venedicto.liganunez.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Component;

import com.venedicto.liganunez.exception.MailSenderException;
import com.venedicto.liganunez.model.ErrorCodes;
import com.venedicto.liganunez.model.http.HttpResponse;
import com.venedicto.liganunez.model.http.User;
import com.venedicto.liganunez.service.UserService;
import com.venedicto.liganunez.utils.HttpUtils;

@Component
public class UserApiHandler {
	private static final Logger log = LoggerFactory.getLogger(UserApiHandler.class);
	@Autowired
	private UserService userService;
	
	public ResponseEntity<HttpResponse> checkUserExistence(HttpResponse response, String email) {
		HttpStatus httpStatus;
		
		try {
			if(userService.userExists(email)) {
				httpStatus = HttpStatus.OK;
				response.setOpCode("200");
			} else {
				httpStatus = HttpStatus.NOT_FOUND;
				response.setOpCode("404");
			}
		} catch(CannotGetJdbcConnectionException e) {
			httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
			response.setOpCode("503");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0002));
			log.error("[Check user] No se pudo establecer conexión con la base de datos");
		} catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setOpCode("500");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0000));
			log.error("[Check user] Ocurrió un error inesperado", e);
		}
		
		return new ResponseEntity<HttpResponse>(response, httpStatus);
	}
	
	public ResponseEntity<HttpResponse> createUser(HttpResponse response, User user) {
		HttpStatus httpStatus;
		
		try {
			userService.createUser(user);
			httpStatus = HttpStatus.OK;
			response.setOpCode("201");
			log.debug("[Create user] Usuario registrado correctamente");
		} catch(CannotGetJdbcConnectionException | MailSenderException e) {
			httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
			response.setOpCode("503");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0002));
			log.error("[Create user] No se pudo establecer conexión con la base de datos", e);
		} catch(DuplicateKeyException e) {
			httpStatus = HttpStatus.BAD_REQUEST;
			response.setOpCode("400");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0006));
			log.error("[Create user] Datos duplicados", e);
		} catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setOpCode("500");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0000));
			log.error("[Create user] Ocurrió un error inesperado", e);
		}
		
		return new ResponseEntity<HttpResponse>(response, httpStatus);
	}
}
