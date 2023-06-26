package com.venedicto.liganunez.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.venedicto.liganunez.model.UserData;

import io.jsonwebtoken.Jwts;

@Component
public class AuthService {
	private PasswordEncoder bCryptEncoder;
	@Value("${session.duration}")
	private int sessionDuration;
	
	public AuthService() {
		bCryptEncoder = new BCryptPasswordEncoder();
	}
	
	/** Password **/
	public String encodePassword(String password) {
		return bCryptEncoder.encode(password);
	}
	
	public boolean passwordMatches(String password, String hash) {
		return BCrypt.checkpw(password, hash);
	}
	
	/** Session Token **/
	public String generateSessionToken(UserData user) {
		String token = Jwts.builder()
				.setId(user.getId())
				.setSubject(user.getData().getEmail())
				.claim("name", user.getData().getName())
				.claim("creationDate", user.getCreationDate())
				.claim("permissions", user.getData().getPermissionLevel())
				.setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from(Instant.now().plus(sessionDuration, ChronoUnit.DAYS)))
				.compact();
		
		return token;
	}
}
