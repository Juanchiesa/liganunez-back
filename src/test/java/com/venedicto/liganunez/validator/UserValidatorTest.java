package com.venedicto.liganunez.validator;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.venedicto.liganunez.model.http.Error;
import com.venedicto.liganunez.model.http.User;

public class UserValidatorTest {
	private UserValidator validator;
	
	@Before
	public void setUp() {
		validator = new UserValidator();
	}
	
	@Test
	public void validateEmail_validEmail() {		
		List<Error> response = validator.validateEmail("unemailvalido@dominio.ext");
		assertEquals(0, response.size());
	}
	
	@Test
	public void validateEmail_invalidEmail() {		
		List<Error> response = validator.validateEmail("unemailvalido--@dominio.extension");
		assertEquals(1, response.size());
	}
	
	@Test
	public void validateUser_validUser() {
		User user = new User();
		user.setName("Prueba");
		user.setAddress("Test address");
		user.setAge(23);
		user.setEmail("unemailvalido@dominio.ext");
		
		List<Error> response = validator.validateUser(user);
		assertEquals(0, response.size());
	}
	
	@Test
	public void validateUser_invalidAge() {
		User user = new User();
		user.setName("Prueba");
		user.setAddress("Test address");
		user.setAge(-23);
		user.setEmail("unemailvalido@dominio.ext");
		
		List<Error> response = validator.validateUser(user);
		assertEquals(1, response.size());
	}
	
	@Test
	public void validateUser_invalidUser() {
		User user = new User();
		
		List<Error> response = validator.validateUser(user);
		assertEquals(4, response.size());
	}
	
	@Test
	public void validateLoginRequest_validRequest() {
		List<Error> response = validator.validateLoginRequest("unemailvalido@dominio.ext", "pass123");
		assertEquals(0, response.size());
	}
	
	@Test
	public void validateLoginRequest_invalidRequest() {
		List<Error> response = validator.validateLoginRequest(null, null);
		assertEquals(2, response.size());
	}
	
	@Test
	public void validatePasswordUpdateRequest_validRequest() {
		List<Error> response = validator.validatePasswordUpdateRequest("12345");
		assertEquals(0, response.size());
	}
	
	@Test
	public void validatePasswordUpdateRequest_invalidRequest() {
		List<Error> response = validator.validatePasswordUpdateRequest("");
		assertEquals(1, response.size());
	}
}