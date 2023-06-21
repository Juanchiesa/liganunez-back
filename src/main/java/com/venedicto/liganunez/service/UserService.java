package com.venedicto.liganunez.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venedicto.liganunez.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public boolean userExists(String email) {
		return userRepository.checkUserExistence(email) == 1;
	}
}
