package com.venedicto.liganunez.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venedicto.liganunez.model.http.User;
import com.venedicto.liganunez.repository.UserRepository;
import com.venedicto.liganunez.utils.EncodingUtils;
import com.venedicto.liganunez.utils.NumberUtils;

@Service
public class UserService {
	private Logger log = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EncodingUtils encodingUtils;
	
	public boolean userExists(String email) {
		return userRepository.checkUserExistence(email) == 1;
	}
	
	public void createUser(User user) {
		String id = UUID.randomUUID().toString();
		log.trace("[Create user] Asignado el ID {} al usuario {}", id, user.getEmail());
		
		String password = NumberUtils.generateRandomNumber(6);
		String encodedPassword = encodingUtils.encode(password);
		log.debug("[Create user] Password asignada correctamente");
		
		userRepository.createUser(id, encodedPassword, user);
		
		//TODO: implementar servicio de mail
	}
}