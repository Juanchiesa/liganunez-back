package com.venedicto.liganunez.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.security.auth.login.LoginException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.venedicto.liganunez.exception.RequestExpiredException;
import com.venedicto.liganunez.model.MailTypes;
import com.venedicto.liganunez.model.PasswordUpdateRequest;
import com.venedicto.liganunez.model.UserData;
import com.venedicto.liganunez.model.http.User;
import com.venedicto.liganunez.model.mail.PasswordUpdateRequestMailTemplate;
import com.venedicto.liganunez.model.mail.SendPasswordMailTemplate;
import com.venedicto.liganunez.repository.UserRepository;
import com.venedicto.liganunez.service.external.MailSenderService;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	@InjectMocks
	private UserService service;
	@Mock
	private AuthService authService;
	@Mock
	private MailSenderService mailService;
	@Mock
	private UserRepository userRepository;
	
	private User user;
	private UserData userData;
	
	@Before
	public void setUp() {
		user = new User();
		user.setName("aa");
		user.setEmail("a@gmail.com");
		user.setAge(23);
		user.setAddress("aaaa");
		
		userData = new UserData();
		userData.setId("aaaa");
		userData.setCreationDate("26/07/2023");
		userData.setLastUpdateDate("26/07/2023");
		userData.setData(user);
	}
	
	@Test
	public void userExists_ok() {
		Mockito.when(userRepository.checkUserExistence("a@gmail.com")).thenReturn(1);
		boolean response = service.userExists("a@gmail.com");
		assertTrue(response);
	}
	@Test
	public void userNotExists_ok() {
		Mockito.when(userRepository.checkUserExistence("a@gmail.com")).thenReturn(0);
		boolean response = service.userExists("a@gmail.com");
		assertFalse(response);
	}
	
	@Test
	public void createUser_ok() {
		Mockito.when(authService.encodePassword(Mockito.anyString())).thenReturn("boca");
		
		service.createUser(user);
		
		Mockito.verify(userRepository, Mockito.times(1)).createUser(Mockito.anyString(), Mockito.eq("boca"), Mockito.any(User.class));
		Mockito.verify(mailService, Mockito.times(1)).sendMail(Mockito.anyString(), Mockito.eq(MailTypes.SEND_PASSWORD), Mockito.any(SendPasswordMailTemplate.class));
	}
	
	@Test
	public void login_ok() throws LoginException {
		user.setAccessKey("dasdsadas");
		UserData userData = new UserData();
		userData.setId("aaaa");
		userData.setCreationDate("26/07/2023");
		userData.setLastUpdateDate("26/07/2023");
		userData.setData(user);
		Mockito.when(userRepository.getUser("a@gmail.com")).thenReturn(userData);
		Mockito.when(authService.passwordMatches("123", "dasdsadas")).thenReturn(true);
		
		UserData response = service.login("a@gmail.com", "123");
		
		assertEquals(response, userData);
	}
	@Test(expected = LoginException.class)
	public void login_wrongPassword() throws LoginException {
		user.setAccessKey("dasdsadas");
		Mockito.when(userRepository.getUser("a@gmail.com")).thenReturn(userData);
		Mockito.when(authService.passwordMatches("123", "dasdsadas")).thenReturn(false);
		
		service.login("a@gmail.com", "123");
	}
	
	@Test
	public void generatePasswordUpdateRequest_ok() {
		service.generatePasswordUpdateRequest("a@gmail.com");
		
		Mockito.verify(userRepository, Mockito.times(1)).createPasswordUpdateRequest(Mockito.eq("a@gmail.com"), Mockito.anyString());
		Mockito.verify(mailService, Mockito.times(1)).sendMail(Mockito.anyString(), Mockito.eq(MailTypes.UPDATE_PASSWORD_REQUEST), Mockito.any(PasswordUpdateRequestMailTemplate.class));
	}
	
	@Test
	public void getPasswordUpdateRequest_ok() {
		PasswordUpdateRequest passwordUpdateRequest = new PasswordUpdateRequest();
		passwordUpdateRequest.setId("xxx");
		passwordUpdateRequest.setUserEmail("a@gmail.com");
		passwordUpdateRequest.setCreationDate(LocalDateTime.now());
		Mockito.when(userRepository.getPasswordUpdateRequest("123")).thenReturn(passwordUpdateRequest);
		
		String response = service.getPasswordUpdateRequest("123");
		
		assertEquals("a@gmail.com", response);
	}
	@Test(expected = RequestExpiredException.class)
	public void getPasswordUpdateRequest_wrongCode() {
		PasswordUpdateRequest passwordUpdateRequest = new PasswordUpdateRequest();
		passwordUpdateRequest.setId("xxx");
		passwordUpdateRequest.setUserEmail("a@gmail.com");
		passwordUpdateRequest.setCreationDate(LocalDateTime.now().minus(40, ChronoUnit.MINUTES));
		Mockito.when(userRepository.getPasswordUpdateRequest("123")).thenReturn(passwordUpdateRequest);
		
		service.getPasswordUpdateRequest("123");
		
		Mockito.verify(userRepository, Mockito.times(1)).deletePasswordUpdateRequest("123");
	}
	
	@Test
	public void generateNewPassword_ok() {
		userData.getData().setAccessKey("dasdas");
		Mockito.when(userRepository.getUser("a@gmail.com")).thenReturn(userData);
		Mockito.when(authService.encodePassword(Mockito.anyString())).thenReturn("boca");
		
		service.generateNewPassword("123", "a@gmail.com");
		
		Mockito.verify(userRepository, Mockito.times(1)).updateUserPassword("a@gmail.com", "boca");
		Mockito.verify(mailService, Mockito.times(1)).sendMail(Mockito.anyString(), Mockito.eq(MailTypes.SEND_NEW_PASSWORD), Mockito.any(SendPasswordMailTemplate.class));
		Mockito.verify(userRepository, Mockito.times(1)).deletePasswordUpdateRequest("123");
	}
	
	@Test
	public void registerDownload_ok() {
		service.registerDownload("aaa", "bbb");
		Mockito.verify(userRepository, Mockito.times(1)).createDownload("aaa", "bbb");
	}
	
	@Test
	public void getUsersStats_ok() {
		Mockito.when(userRepository.getUsersStats()).thenReturn(2);
		int response = service.getUsersStats();
		assertEquals(2, response);
	}
}