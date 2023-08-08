package com.venedicto.liganunez.handler;

import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Component;

import com.venedicto.liganunez.exception.MailSenderException;
import com.venedicto.liganunez.exception.RequestExpiredException;
import com.venedicto.liganunez.model.ErrorCodes;
import com.venedicto.liganunez.model.UserData;
import com.venedicto.liganunez.model.http.HttpResponse;
import com.venedicto.liganunez.model.http.User;
import com.venedicto.liganunez.model.http.UserLoginHttpResponse;
import com.venedicto.liganunez.model.http.UserStatsResponse;
import com.venedicto.liganunez.service.AuthService;
import com.venedicto.liganunez.service.UserService;
import com.venedicto.liganunez.utils.HttpUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class UserApiHandler {
	private static final Logger log = LoggerFactory.getLogger(UserApiHandler.class);
	@Autowired
	private AuthService authService;
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
			httpStatus = HttpStatus.CREATED;
			response.setOpCode("201");
			log.debug("[Create user] Usuario registrado correctamente");
		} catch(CannotGetJdbcConnectionException e) {
			httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
			response.setOpCode("503");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0002));
			log.error("[Create user] No se pudo establecer conexión con la base de datos", e);
		} catch(MailSenderException e) {
			httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
			response.setOpCode("503");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0002));
			log.error("[Create user] No se pudo establecer conexión con el servicio de correo electrónico", e);
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
	
	public ResponseEntity<UserLoginHttpResponse> login(UserLoginHttpResponse response, String email, String password) {
		HttpStatus httpStatus;
		
		try {
			UserData user = userService.login(email, password);
			response.setData(user.getData());
			
			String token = authService.generateSessionToken(user);
			response.setToken(token);
			
			httpStatus = HttpStatus.OK;
			response.setOpCode("200");
			log.debug("[Login] Acceso aprobado");
		} catch(CannotGetJdbcConnectionException e) {
			httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
			response.setOpCode("503");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0002));
			log.error("[Login] No se pudo establecer conexión con la base de datos", e);
		} catch(EmptyResultDataAccessException e) {
			httpStatus = HttpStatus.NOT_FOUND;
			response.setOpCode("404");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0008));
			log.error("[Login] El usuario ingresado no se encuentra registrado", e);
		}  catch(LoginException e) {
			httpStatus = HttpStatus.BAD_REQUEST;
			response.setOpCode("400");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0009));
			log.error("[Login] Clave incorrecta", e);
		} catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setOpCode("500");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0000));
			log.error("[Login] Ocurrió un error inesperado", e);
		}
		
		return new ResponseEntity<UserLoginHttpResponse>(response, httpStatus);
	}
	
	public ResponseEntity<HttpResponse> requestUserPasswordUpdate(HttpResponse response, String email) {
		HttpStatus httpStatus;
		
		try {
			userService.generatePasswordUpdateRequest(email);
			httpStatus = HttpStatus.CREATED;
			response.setOpCode("201");
			log.debug("[Password update request] Solicitud generada");
		} catch(DuplicateKeyException e) {
			httpStatus = HttpStatus.BAD_REQUEST;
			response.setOpCode("400");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0010));
			log.error("[Password update request] Datos duplicados", e);
		} catch(CannotGetJdbcConnectionException e) {
			httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
			response.setOpCode("503");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0002));
			log.error("[Password update request] No se pudo establecer conexión con la base de datos", e);
		} catch(MailSenderException e) {
			httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
			response.setOpCode("503");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0002));
			log.error("[Password update request] No se pudo establecer conexión con el servicio de correo electrónico", e);
		} catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setOpCode("500");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0000));
			log.error("[Password update request] Ocurrió un error inesperado", e);
		}
		
		return new ResponseEntity<HttpResponse>(response, httpStatus);
	}

	public ResponseEntity<HttpResponse> updateUserPassword(HttpResponse response, String requestCode) {
		HttpStatus httpStatus;
		
		try {
			String userEmail = userService.getPasswordUpdateRequest(requestCode);
			userService.generateNewPassword(requestCode, userEmail);
			httpStatus = HttpStatus.OK;
			response.setOpCode("200");
		} catch(EmptyResultDataAccessException e) {
			httpStatus = HttpStatus.NOT_FOUND;
			response.setOpCode("404");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0012));
			log.error("[Password update] El código ingresado es inexistente", e);
		} catch(RequestExpiredException e) {
			httpStatus = HttpStatus.BAD_REQUEST;
			response.setOpCode("400");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0013));
			log.error("[Password update] El código ingresado está vencido", e);
		} catch(CannotGetJdbcConnectionException e) {
			httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
			response.setOpCode("503");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0002));
			log.error("[Password update] No se pudo establecer conexión con la base de datos", e);
		} catch(MailSenderException e) {
			httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
			response.setOpCode("503");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0002));
			log.error("[Password update] No se pudo establecer conexión con el servicio de correo electrónico", e);
		} catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setOpCode("500");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0000));
			log.error("[Password update] Ocurrió un error inesperado", e);
		}
		
		return new ResponseEntity<HttpResponse>(response, httpStatus);
	}
	
	public ResponseEntity<HttpResponse> registerDownload(HttpResponse response, String token, String pictureId) {
		HttpStatus httpStatus;
		
		try {
			UserData user = authService.readSessionToken(token);
			log.trace("[Register download] El usuario {} recibió acceso para descargar las imagen", user.getId());
			
			userService.registerDownload(pictureId, user.getId());
			
			httpStatus = HttpStatus.OK;
			response.setOpCode("200");
		} catch(MalformedJwtException | IllegalArgumentException e) {
			httpStatus = HttpStatus.FORBIDDEN;
			response.setOpCode("403");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0015));
			log.error("[Register download] Token inválido", e);
		} catch(ExpiredJwtException e) {
			httpStatus = HttpStatus.UNAUTHORIZED;
			response.setOpCode("401");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0016));
			log.error("[Register download] Token expirado", e);
		} catch(CannotGetJdbcConnectionException e) {
			httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
			response.setOpCode("503");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0002));
			log.error("[Register download] No se pudo establecer conexión con la base de datos", e);
		} catch(DataIntegrityViolationException e) {
			httpStatus = HttpStatus.NOT_FOUND;
			response.setOpCode("404");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0023));
			log.error("[Register download] La imagen es inexistente", e);
		} catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setOpCode("500");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0000));
			log.error("[Register download] Ocurrió un error inesperado", e);
		}
		
		return new ResponseEntity<HttpResponse>(response, httpStatus);
	}
	
	public ResponseEntity<UserStatsResponse> getUsersStats(UserStatsResponse response) {
		HttpStatus httpStatus;
		
		try {
			int totalUsers = userService.getUsersStats();
			response.setData(totalUsers);
			httpStatus = HttpStatus.OK;
			response.setOpCode("200");
		} catch(CannotGetJdbcConnectionException e) {
			httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
			response.setOpCode("503");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0002));
			log.error("[Users stats] No se pudo establecer conexión con la base de datos", e);
		} catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setOpCode("500");
			response.addErrorsItem(HttpUtils.generateError(ErrorCodes.LN0000));
			log.error("[Users stats] Ocurrió un error inesperado", e);
		}
		
		return new ResponseEntity<UserStatsResponse>(response, httpStatus);
	}
}