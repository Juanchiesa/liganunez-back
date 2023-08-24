package com.venedicto.liganunez.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.venedicto.liganunez.handler.UserApiHandler;
import com.venedicto.liganunez.model.ErrorCodes;
import com.venedicto.liganunez.model.http.Error;
import com.venedicto.liganunez.model.http.GetUsersHttpResponse;
import com.venedicto.liganunez.model.http.HttpResponse;
import com.venedicto.liganunez.model.http.User;
import com.venedicto.liganunez.model.http.UserLoginHttpResponse;
import com.venedicto.liganunez.model.http.UserStatsResponse;
import com.venedicto.liganunez.utils.HttpUtils;
import com.venedicto.liganunez.validator.UserValidator;

@RunWith(MockitoJUnitRunner.class)
public class UserApiControllerTest {
	@InjectMocks
	private UserApiController controller;
	@Mock
	private UserApiHandler handler;
	@Mock
	private UserValidator validator;
	
	@Test
	public void checkUserExistence_ok() {
		controller.checkUserExistence("abc@gmail.com");
		Mockito.verify(handler, Mockito.times(1)).checkUserExistence(Mockito.any(HttpResponse.class), Mockito.eq("abc@gmail.com"));
	}
	@Test
	public void checkUserExistence_error() {
		List<Error> errors = new ArrayList<>();
		errors.add(HttpUtils.generateError(ErrorCodes.LN0001));
		
		Mockito.when(validator.validateEmail("abc@gmail.com")).thenReturn(errors);
		controller.checkUserExistence("abc@gmail.com");
		Mockito.verify(handler, Mockito.times(0)).checkUserExistence(Mockito.any(HttpResponse.class), Mockito.eq("abc@gmail.com"));
	}
	
	@Test
	public void createUser_ok() {
		controller.createUser(new User());
		Mockito.verify(handler, Mockito.times(1)).createUser(Mockito.any(HttpResponse.class), Mockito.any(User.class));
	}
	@Test
	public void createUser_error() {
		List<Error> errors = new ArrayList<>();
		errors.add(HttpUtils.generateError(ErrorCodes.LN0001));
		errors.add(HttpUtils.generateError(ErrorCodes.LN0003));
		errors.add(HttpUtils.generateError(ErrorCodes.LN0004));
		errors.add(HttpUtils.generateError(ErrorCodes.LN0005));
		
		Mockito.when(validator.validateUser(Mockito.any(User.class))).thenReturn(errors);
		controller.createUser(new User());
		Mockito.verify(handler, Mockito.times(0)).createUser(Mockito.any(HttpResponse.class), Mockito.any(User.class));
	}
	
	@Test
	public void loginUser_ok() {
		controller.loginUser("a@gmail.com", "123");
		Mockito.verify(handler, Mockito.times(1)).login(Mockito.any(UserLoginHttpResponse.class), Mockito.eq("a@gmail.com"), Mockito.eq("123"));
	}
	@Test
	public void loginUser_error() {
		List<Error> errors = new ArrayList<>();
		errors.add(HttpUtils.generateError(ErrorCodes.LN0001));
		errors.add(HttpUtils.generateError(ErrorCodes.LN0007));
		
		Mockito.when(validator.validateLoginRequest("a@gmail.com", "123")).thenReturn(errors);
		controller.loginUser("a@gmail.com", "123");
		Mockito.verify(handler, Mockito.times(0)).login(Mockito.any(UserLoginHttpResponse.class), Mockito.eq("a@gmail.com"), Mockito.eq("123"));
	}
	
	@Test
	public void requestUserPasswordUpdate_ok() {
		controller.requestUserPasswordUpdate("a@gmail.com");
		Mockito.verify(handler, Mockito.times(1)).requestUserPasswordUpdate(Mockito.any(HttpResponse.class), Mockito.eq("a@gmail.com"));
	}
	@Test
	public void requestUserPasswordUpdate_error() {
		List<Error> errors = new ArrayList<>();
		errors.add(HttpUtils.generateError(ErrorCodes.LN0001));
		
		Mockito.when(validator.validateEmail("a@gmail.com")).thenReturn(errors);
		controller.requestUserPasswordUpdate("a@gmail.com");
		Mockito.verify(handler, Mockito.times(0)).requestUserPasswordUpdate(Mockito.any(HttpResponse.class), Mockito.eq("a@gmail.com"));
	}
	
	@Test
	public void updateUserPassword_ok() {
		controller.updateUserPassword("123");
		Mockito.verify(handler, Mockito.times(1)).updateUserPassword(Mockito.any(HttpResponse.class), Mockito.eq("123"));
	}
	@Test
	public void updateUserPassword_error() {
		List<Error> errors = new ArrayList<>();
		errors.add(HttpUtils.generateError(ErrorCodes.LN0011));
		
		Mockito.when(validator.validatePasswordUpdateRequest("123")).thenReturn(errors);
		controller.updateUserPassword("123");
		Mockito.verify(handler, Mockito.times(0)).updateUserPassword(Mockito.any(HttpResponse.class), Mockito.eq("123"));
	}
	
	@Test
	public void downloadPicture_ok() {
		controller.downloadPicture("aaaa", "12345");
		Mockito.verify(handler, Mockito.times(1)).registerDownload(Mockito.any(HttpResponse.class), Mockito.eq("12345"), Mockito.eq("aaaa"));
	}
	
	@Test
	public void getUsersStats_ok() {
		controller.getUserStats("12345");
		Mockito.verify(handler, Mockito.times(1)).getUsersStats(Mockito.any(UserStatsResponse.class), Mockito.eq("12345"));
	}
	
	@Test
	public void getUsers_ok() {
		controller.getUsers("12345");
		Mockito.verify(handler, Mockito.times(1)).getUsers(Mockito.any(GetUsersHttpResponse.class), Mockito.eq("12345"));
	}
}