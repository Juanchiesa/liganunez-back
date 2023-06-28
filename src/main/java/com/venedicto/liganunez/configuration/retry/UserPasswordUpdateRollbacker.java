package com.venedicto.liganunez.configuration.retry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.venedicto.liganunez.model.mail.SendPasswordMailTemplate;
import com.venedicto.liganunez.repository.UserRepository;

@Component
public class UserPasswordUpdateRollbacker extends Rollbacker {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void rollback(Object[] parameters) {
		String email = (String) parameters[0];
		SendPasswordMailTemplate mailTemplate = (SendPasswordMailTemplate) parameters[2];
		userRepository.updateUserPassword(email, mailTemplate.getOldAccessKey());
	}
}