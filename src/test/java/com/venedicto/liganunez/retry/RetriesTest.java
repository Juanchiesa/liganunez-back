package com.venedicto.liganunez.retry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.venedicto.liganunez.exception.MailSenderException;
import com.venedicto.liganunez.model.MailTypes;
import com.venedicto.liganunez.model.http.User;
import com.venedicto.liganunez.model.mail.PasswordUpdateRequestMailTemplate;
import com.venedicto.liganunez.model.mail.SendPasswordMailTemplate;
import com.venedicto.liganunez.repository.UserRepository;
import com.venedicto.liganunez.service.external.MailSenderService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
@PropertySource("classpath:application.properties")
@ComponentScan("com.venedicto.liganunez")
@Import(UserRepository.class)
public class RetriesTest {
	@Autowired
	private UserRepository dbRepository;
	@Mock
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MailSenderService mailSenderService;
	@Mock
	private RestTemplate restTemplate;
	
	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		ReflectionTestUtils.setField(dbRepository, "jdbcTemplate", jdbcTemplate);
		ReflectionTestUtils.setField(mailSenderService, "restTemplate", restTemplate);
	}
	
	@Test(expected = CannotGetJdbcConnectionException.class)
	public void dbRetry_ok() {
		User user = new User();
		user.setEmail("abc@gmail.com");
		user.setName("Test");
		user.setAddress("Address");
		user.setAge(23);
		Mockito.doThrow(CannotGetJdbcConnectionException.class).when(jdbcTemplate).update(Mockito.anyString(), Mockito.eq("xxx-xx-x"), Mockito.eq("abc@gmail.com"), Mockito.eq("1234"), Mockito.eq("Test"), Mockito.eq(23), Mockito.eq("Address"));
		
		dbRepository.createUser("xxx-xx-x", "1234", user);
		
		Mockito.verify(jdbcTemplate, Mockito.times(3)).update(Mockito.anyString(), Mockito.eq("xxx-xx-x"), Mockito.eq("abc@gmail.com"), Mockito.eq("1234"), Mockito.eq("Test"), Mockito.eq(23), Mockito.eq("Address"));
	}
	
	@Test(expected = MailSenderException.class)
	public void mailSenderRetry_sendPassword_ok() {
		SendPasswordMailTemplate mailData = new SendPasswordMailTemplate();
		mailData.setAccessKey("123");
		
		Mockito.doThrow(RuntimeException.class).when(restTemplate).postForEntity(Mockito.anyString(), Mockito.any(), Mockito.eq(String.class));
		
		mailSenderService.sendMail("abc@gmail.com", MailTypes.SEND_PASSWORD, mailData);
		
		Mockito.verify(restTemplate, Mockito.times(3)).postForEntity(Mockito.anyString(), Mockito.any(), Mockito.eq(String.class));
	}
	
	@Test(expected = MailSenderException.class)
	public void mailSenderRetry_updatePasswordRequest_ok() {
		PasswordUpdateRequestMailTemplate mailData = new PasswordUpdateRequestMailTemplate();
		mailData.setRequestCode("12345");
		
		Mockito.doThrow(RuntimeException.class).when(restTemplate).postForEntity(Mockito.anyString(), Mockito.any(), Mockito.eq(String.class));
		
		mailSenderService.sendMail("abc@gmail.com", MailTypes.UPDATE_PASSWORD_REQUEST, mailData);
		
		Mockito.verify(restTemplate, Mockito.times(3)).postForEntity(Mockito.anyString(), Mockito.any(), Mockito.eq(String.class));
	}
	
	@Test(expected = MailSenderException.class)
	public void mailSenderRetry_passwordUpdate_ok() {
		SendPasswordMailTemplate mailData = new SendPasswordMailTemplate();
		mailData.setAccessKey("123");
		
		Mockito.doThrow(RuntimeException.class).when(restTemplate).postForEntity(Mockito.anyString(), Mockito.any(), Mockito.eq(String.class));
		
		mailSenderService.sendMail("abc@gmail.com", MailTypes.SEND_NEW_PASSWORD, mailData);
		
		Mockito.verify(restTemplate, Mockito.times(3)).postForEntity(Mockito.anyString(), Mockito.any(), Mockito.eq(String.class));
	}
}