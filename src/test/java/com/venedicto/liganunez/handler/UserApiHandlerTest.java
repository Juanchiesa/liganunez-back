package com.venedicto.liganunez.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.security.auth.login.LoginException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

import com.venedicto.liganunez.exception.MailSenderException;
import com.venedicto.liganunez.exception.RequestExpiredException;
import com.venedicto.liganunez.model.UserData;
import com.venedicto.liganunez.model.http.HttpResponse;
import com.venedicto.liganunez.model.http.User;
import com.venedicto.liganunez.model.http.UserLoginHttpResponse;
import com.venedicto.liganunez.model.http.UserStatsResponse;
import com.venedicto.liganunez.service.AuthService;
import com.venedicto.liganunez.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@RunWith(MockitoJUnitRunner.class)
public class UserApiHandlerTest {
	@InjectMocks
	private UserApiHandler handler;
	@Mock
	private AuthService authService;
	@Mock
	private UserService userService;
	
	@Test
	public void checkUserExistence_ok_exists() {
		Mockito.when(userService.userExists("a@gmail.com")).thenReturn(true);
		ResponseEntity<HttpResponse> response = handler.checkUserExistence(new HttpResponse(), "a@gmail.com");
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	@Test
	public void checkUserExistence_ok_NotExists() {
		Mockito.when(userService.userExists("a@gmail.com")).thenReturn(false);
		ResponseEntity<HttpResponse> response = handler.checkUserExistence(new HttpResponse(), "a@gmail.com");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	@Test
	public void checkUserExistence_dbTimeout() {
		Mockito.when(userService.userExists("a@gmail.com")).thenThrow(CannotGetJdbcConnectionException.class);
		ResponseEntity<HttpResponse> response = handler.checkUserExistence(new HttpResponse(), "a@gmail.com");
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
	}
	@Test
	public void checkUserExistence_internalError() {
		Mockito.when(userService.userExists("a@gmail.com")).thenThrow(RuntimeException.class);
		ResponseEntity<HttpResponse> response = handler.checkUserExistence(new HttpResponse(), "a@gmail.com");
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	
	@Test
	public void createUser_ok() {
		ResponseEntity<HttpResponse> response = handler.createUser(new HttpResponse(), new User());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	@Test
	public void createUser_dbTimeout() {
		Mockito.doThrow(CannotGetJdbcConnectionException.class).when(userService).createUser(Mockito.any(User.class));
		ResponseEntity<HttpResponse> response = handler.createUser(new HttpResponse(), new User());
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
	}
	@Test
	public void createUser_mailSenderTimeout() {
		Mockito.doThrow(MailSenderException.class).when(userService).createUser(Mockito.any(User.class));
		ResponseEntity<HttpResponse> response = handler.createUser(new HttpResponse(), new User());
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
	}
	@Test
	public void createUser_duplicatedUser() {
		Mockito.doThrow(DuplicateKeyException.class).when(userService).createUser(Mockito.any(User.class));
		ResponseEntity<HttpResponse> response = handler.createUser(new HttpResponse(), new User());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	@Test
	public void createUser_internalError() {
		Mockito.doThrow(RuntimeException.class).when(userService).createUser(Mockito.any(User.class));
		ResponseEntity<HttpResponse> response = handler.createUser(new HttpResponse(), new User());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	
	@Test
	public void login_ok() throws LoginException {
		Mockito.when(userService.login("a@gmail.com", "123")).thenReturn(new UserData());
		ResponseEntity<UserLoginHttpResponse> response = handler.login(new UserLoginHttpResponse(), "a@gmail.com", "123");
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	@Test
	public void login_dbTimeout() throws LoginException {
		Mockito.when(userService.login("a@gmail.com", "123")).thenThrow(CannotGetJdbcConnectionException.class);
		ResponseEntity<UserLoginHttpResponse> response = handler.login(new UserLoginHttpResponse(), "a@gmail.com", "123");
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
	}
	@Test
	public void login_wrongUser() throws LoginException {
		Mockito.when(userService.login("a@gmail.com", "123")).thenThrow(EmptyResultDataAccessException.class);
		ResponseEntity<UserLoginHttpResponse> response = handler.login(new UserLoginHttpResponse(), "a@gmail.com", "123");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	@Test
	public void login_wrongPassword() throws LoginException {
		Mockito.when(userService.login("a@gmail.com", "123")).thenThrow(LoginException.class);
		ResponseEntity<UserLoginHttpResponse> response = handler.login(new UserLoginHttpResponse(), "a@gmail.com", "123");
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	@Test
	public void login_internalError() throws LoginException {
		Mockito.when(userService.login("a@gmail.com", "123")).thenThrow(RuntimeException.class);
		ResponseEntity<UserLoginHttpResponse> response = handler.login(new UserLoginHttpResponse(), "a@gmail.com", "123");
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	
	@Test
	public void requestUserPasswordUpdate_ok() {
		ResponseEntity<HttpResponse> response = handler.requestUserPasswordUpdate(new HttpResponse(), "a@gmail.com");
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	@Test
	public void requestUserPasswordUpdate_dbTimeout() {
		Mockito.doThrow(CannotGetJdbcConnectionException.class).when(userService).generatePasswordUpdateRequest("a@gmail.com");
		ResponseEntity<HttpResponse> response = handler.requestUserPasswordUpdate(new HttpResponse(), "a@gmail.com");
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
	}
	@Test
	public void requestUserPasswordUpdate_requestDuplicated() {
		Mockito.doThrow(DuplicateKeyException.class).when(userService).generatePasswordUpdateRequest("a@gmail.com");
		ResponseEntity<HttpResponse> response = handler.requestUserPasswordUpdate(new HttpResponse(), "a@gmail.com");
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	@Test
	public void requestUserPasswordUpdate_mailSenderTimeout() {
		Mockito.doThrow(MailSenderException.class).when(userService).generatePasswordUpdateRequest("a@gmail.com");
		ResponseEntity<HttpResponse> response = handler.requestUserPasswordUpdate(new HttpResponse(), "a@gmail.com");
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
	}
	@Test
	public void requestUserPasswordUpdate_internalError() {
		Mockito.doThrow(RuntimeException.class).when(userService).generatePasswordUpdateRequest("a@gmail.com");
		ResponseEntity<HttpResponse> response = handler.requestUserPasswordUpdate(new HttpResponse(), "a@gmail.com");
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	
	@Test
	public void updateUserPassword_ok() {
		ResponseEntity<HttpResponse> response = handler.updateUserPassword(new HttpResponse(), "12345");
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	@Test
	public void updateUserPassword_dbTimeout() {
		Mockito.when(userService.getPasswordUpdateRequest("12345")).thenThrow(CannotGetJdbcConnectionException.class);
		ResponseEntity<HttpResponse> response = handler.updateUserPassword(new HttpResponse(), "12345");
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
	}
	@Test
	public void updateUserPassword_wrongCode() {
		Mockito.when(userService.getPasswordUpdateRequest("12345")).thenThrow(EmptyResultDataAccessException.class);
		ResponseEntity<HttpResponse> response = handler.updateUserPassword(new HttpResponse(), "12345");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	@Test
	public void updateUserPassword_expiredCode() {
		Mockito.when(userService.getPasswordUpdateRequest("12345")).thenThrow(RequestExpiredException.class);
		ResponseEntity<HttpResponse> response = handler.updateUserPassword(new HttpResponse(), "12345");
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	@Test
	public void updateUserPassword_mailSenderTimeout() {
		Mockito.doThrow(MailSenderException.class).when(userService).generateNewPassword("12345", null);
		ResponseEntity<HttpResponse> response = handler.updateUserPassword(new HttpResponse(), "12345");
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
	}
	@Test
	public void updateUserPassword_internalError() {
		Mockito.when(userService.getPasswordUpdateRequest("12345")).thenThrow(RuntimeException.class);
		ResponseEntity<HttpResponse> response = handler.updateUserPassword(new HttpResponse(), "12345");
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	
	@Test
	public void registerDownload_ok() {
		Mockito.when(authService.readSessionToken("tokenlog")).thenReturn(new UserData());
		ResponseEntity<HttpResponse> response = handler.registerDownload(new HttpResponse(), "tokenlog", "xxxx");
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	@Test
	public void registerDownload_invalidToken() {
		Mockito.when(authService.readSessionToken("tokenlog")).thenThrow(MalformedJwtException.class);
		ResponseEntity<HttpResponse> response = handler.registerDownload(new HttpResponse(), "tokenlog", "xxxx");
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	@Test
	public void registerDownload_expiredToken() {
		Mockito.when(authService.readSessionToken("tokenlog")).thenThrow(ExpiredJwtException.class);
		ResponseEntity<HttpResponse> response = handler.registerDownload(new HttpResponse(), "tokenlog", "xxxx");
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}
	@Test
	public void registerDownload_dbTimeout() {
		Mockito.when(authService.readSessionToken("tokenlog")).thenReturn(new UserData());
		Mockito.doThrow(CannotGetJdbcConnectionException.class).when(userService).registerDownload("xxxx", null);
		ResponseEntity<HttpResponse> response = handler.registerDownload(new HttpResponse(), "tokenlog", "xxxx");
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
	}
	@Test
	public void registerDownload_wrongImage() {
		Mockito.when(authService.readSessionToken("tokenlog")).thenReturn(new UserData());
		Mockito.doThrow(DataIntegrityViolationException.class).when(userService).registerDownload("xxxx", null);
		ResponseEntity<HttpResponse> response = handler.registerDownload(new HttpResponse(), "tokenlog", "xxxx");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	@Test
	public void registerDownload_internalError() {
		Mockito.when(authService.readSessionToken("tokenlog")).thenReturn(new UserData());
		Mockito.doThrow(RuntimeException.class).when(userService).registerDownload("xxxx", null);
		ResponseEntity<HttpResponse> response = handler.registerDownload(new HttpResponse(), "tokenlog", "xxxx");
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	
	@Test
	public void getUsersStats_ok() {
		ResponseEntity<UserStatsResponse> response = handler.getUsersStats(new UserStatsResponse());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	@Test
	public void getUsersStats_dbTimeout() {
		Mockito.when(userService.getUsersStats()).thenThrow(CannotGetJdbcConnectionException.class);
		ResponseEntity<UserStatsResponse> response = handler.getUsersStats(new UserStatsResponse());
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
	}
	@Test
	public void getUsersStats_internalError() {
		Mockito.when(userService.getUsersStats()).thenThrow(RuntimeException.class);
		ResponseEntity<UserStatsResponse> response = handler.getUsersStats(new UserStatsResponse());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
}