package com.venedicto.liganunez.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.venedicto.liganunez.configuration.retry.Rollbacker;
import com.venedicto.liganunez.configuration.retry.UserCreationRollbacker;
import com.venedicto.liganunez.model.MailTypes;

import lombok.Setter;

@Component
public class MailSenderException extends RuntimeException {
	private static final long serialVersionUID = 9152993931321291166L;
	@Setter
	private String message;
	@Autowired
	private UserCreationRollbacker userCreationRollbacker;
	
	public Rollbacker getRollbacker(MailTypes mailType) {
		if(mailType.equals(MailTypes.ACCOUNT_CREATED)) {
			return userCreationRollbacker;
		}
		
		return null;
	}
}
