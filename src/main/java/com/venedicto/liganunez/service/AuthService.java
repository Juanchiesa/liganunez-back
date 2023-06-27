package com.venedicto.liganunez.service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.venedicto.liganunez.model.UserData;
import com.venedicto.liganunez.utils.converter.ClaimsConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class AuthService {
	private PasswordEncoder bCryptEncoder;
	private Key hmacKey;
	
	@Value("${session.duration}")
	private int sessionDuration;
	@Value("${session.secret.key}")
	private String secretKey;
	
	@Autowired
	private ClaimsConverter claimsConverter;
	
	@PostConstruct
	public void authService() {
		bCryptEncoder = new BCryptPasswordEncoder();
		hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secretKey), SignatureAlgorithm.HS256.getJcaName());
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
				.signWith(hmacKey)
				.compact();
		
		return encodeSessionToken(token);
	}
	
	public UserData readSessionToken(String token) {
		String decodedToken = decodeSessionToken(token);
		
		Jws<Claims> jwt = Jwts.parserBuilder()
	            .setSigningKey(hmacKey)
	            .build()
	            .parseClaimsJws(decodedToken);
		
		return claimsConverter.claimsToUser(jwt.getBody());
	}
	
	private String encodeSessionToken(String token) {
		StringBuilder encodedTokenBuilder = new StringBuilder();
		
		String base64EncodedToken = Base64.getEncoder().encodeToString(token.getBytes());
		encodedTokenBuilder.append(base64EncodedToken);
		encodedTokenBuilder.reverse();
		encodedTokenBuilder.replace(0, 2, "Ln");
		
		return encodedTokenBuilder.toString();
	}
	
	private String decodeSessionToken(String encodedToken) {
		StringBuilder decodedTokenBuilder = new StringBuilder();
		decodedTokenBuilder.append(encodedToken);
		decodedTokenBuilder.replace(0, -2, "==");
		
		byte[] decodedToken = decodedTokenBuilder.toString().getBytes();
		
		return Base64.getDecoder().decode(decodedToken).toString();
	}
}
