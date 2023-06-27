package com.venedicto.liganunez.utils.converter;

import org.springframework.stereotype.Component;

import com.venedicto.liganunez.model.UserData;
import com.venedicto.liganunez.model.http.User;

import io.jsonwebtoken.Claims;

@Component
public class ClaimsConverter {
	public UserData claimsToUser(Claims claims) {
		UserData user = new UserData();
		
		user.setId(claims.getId());
		user.setCreationDate((String) claims.get("creationDate"));
		user.setData(claimsToUserData(claims));
		
		return user;
	}
	
	private User claimsToUserData(Claims claims) {
		User userData = new User();
		
		userData.setEmail(claims.getSubject());
		userData.setName((String) claims.get("name"));
		userData.setPermissionLevel((Integer) claims.get("permissions"));
		
		return userData;
	}
}
