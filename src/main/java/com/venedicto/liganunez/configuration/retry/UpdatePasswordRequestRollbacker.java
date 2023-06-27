package com.venedicto.liganunez.configuration.retry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.venedicto.liganunez.model.mail.PasswordUpdateRequestMailTemplate;
import com.venedicto.liganunez.repository.UserRepository;

@Component
public class UpdatePasswordRequestRollbacker extends Rollbacker {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void rollback(Object[] parameters) {
		PasswordUpdateRequestMailTemplate mailTemplate = (PasswordUpdateRequestMailTemplate) parameters[2];
		userRepository.deletePasswordUpdateRequest(mailTemplate.getRequestCode());
	}
}