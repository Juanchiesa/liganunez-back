package com.venedicto.liganunez.configuration.retry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.venedicto.liganunez.repository.UserRepository;

@Component
public class UserCreationRollbacker extends Rollbacker {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void rollback(Object[] parameters) {
		String email = (String) parameters[0];
		userRepository.deleteUser(email);
	}
}