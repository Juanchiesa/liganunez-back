package com.venedicto.liganunez.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import com.venedicto.liganunez.model.PasswordUpdateRequest;
import com.venedicto.liganunez.model.UserData;
import com.venedicto.liganunez.model.http.User;
import com.venedicto.liganunez.repository.mappers.PasswordUpdateRequestRowMapper;
import com.venedicto.liganunez.repository.mappers.UserDataRowMapper;
import com.venedicto.liganunez.repository.mappers.UserRowMapper;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {
	@InjectMocks
	private UserRepository repository;
	@Mock
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void checkUserExistence_ok() {
		Mockito.when(jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.eq(Integer.class), Mockito.anyString())).thenReturn(1);
		
		int response = repository.checkUserExistence("abc@gmail.com");
		
		assertEquals(1, response);
		Mockito.verify(jdbcTemplate, Mockito.times(1)).queryForObject(Mockito.anyString(), Mockito.eq(Integer.class), Mockito.eq("abc@gmail.com"));
	}
	
	@Test
	public void createUser_ok() {
		User user = new User();
		user.setEmail("abc@gmail.com");
		user.setAccessKey("1234");
		user.setName("Test");
		user.setAddress("Address");
		user.setAge(23);
		
		repository.createUser("xxx-xx-x", user);
		Mockito.verify(jdbcTemplate, Mockito.times(1)).update(Mockito.anyString(), Mockito.eq("xxx-xx-x"), Mockito.eq("abc@gmail.com"), Mockito.eq("1234"), Mockito.eq("Test"), Mockito.eq(23), Mockito.eq("Address"));
	}
	
	@Test
	public void getUser_ok() {
		UserData user = new UserData();
		user.setId("xxxx-xxx-xx-x");
		Mockito.when(jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.any(UserRowMapper.class), Mockito.eq("abcd@gmail.com"))).thenReturn(user);
		
		UserData response = repository.getUser("abcd@gmail.com");
		
		assertEquals("xxxx-xxx-xx-x", response.getId());
	}
	
	@Test
	public void updateUserPassword_ok() {
		repository.updateUserPassword("abcd@gmail.com", "54321");
		Mockito.verify(jdbcTemplate, Mockito.times(1)).update(Mockito.anyString(), Mockito.eq("54321"), Mockito.eq("abcd@gmail.com"));
	}
	
	@Test
	public void deleteUser_ok() {
		repository.deleteUser("abcd@gmail.com");
		Mockito.verify(jdbcTemplate, Mockito.times(1)).update(Mockito.anyString(), Mockito.eq("abcd@gmail.com"));
	}
	
	@Test
	public void createPasswordUpdateRequest_ok() {
		repository.createPasswordUpdateRequest("abcd@gmail.com", "1234");
		Mockito.verify(jdbcTemplate, Mockito.times(1)).update(Mockito.anyString(), Mockito.eq("1234"), Mockito.eq("abcd@gmail.com"));
	}
	
	@Test
	public void getPasswordUpdateRequest_ok() {
		PasswordUpdateRequest request = new PasswordUpdateRequest();
		request.setId("xxxx-xxx-xx-x");
		
		Mockito.when(jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.any(PasswordUpdateRequestRowMapper.class), Mockito.eq("12345"))).thenReturn(request);
		
		PasswordUpdateRequest response = repository.getPasswordUpdateRequest("12345");
		assertEquals("xxxx-xxx-xx-x", response.getId());
	}
	
	@Test
	public void deletePasswordUpdateRequest_ok() {
		repository.deletePasswordUpdateRequest("12345");
		Mockito.verify(jdbcTemplate, Mockito.times(1)).update(Mockito.anyString(), Mockito.eq("12345"));
	}
	
	@Test
	public void createDownload_ok() {
		repository.createDownload("xxxx-xxx-xx-x", "aaa-aa-a");
		Mockito.verify(jdbcTemplate, Mockito.times(1)).update(Mockito.anyString(), Mockito.eq("xxxx-xxx-xx-x"), Mockito.eq("aaa-aa-a"));
	}
	
	@Test
	public void getUsersStats_ok() {
		Mockito.when(jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.eq(Integer.class))).thenReturn(1);
		repository.getUsersStats();
		Mockito.verify(jdbcTemplate, Mockito.times(1)).queryForObject(Mockito.anyString(), Mockito.eq(Integer.class));
	}
	
	@Test
	public void getUsers_ok() {
		repository.getUsers();
		Mockito.verify(jdbcTemplate, Mockito.times(1)).query(Mockito.anyString(), Mockito.any(UserDataRowMapper.class));
	}
}