package com.venedicto.liganunez.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.venedicto.liganunez.configuration.retry.Rollbacker;
import com.venedicto.liganunez.configuration.retry.UpdatePasswordRequestRollbacker;
import com.venedicto.liganunez.configuration.retry.UserCreationRollbacker;
import com.venedicto.liganunez.configuration.retry.UserPasswordUpdateRollbacker;
import com.venedicto.liganunez.model.MailTypes;

import lombok.Getter;
import lombok.Setter;

@Component
public class MailSenderException extends RuntimeException {
	private static final long serialVersionUID = 9152993931321291166L;
	@Getter @Setter
	private String message;
	@Autowired
	private UserCreationRollbacker userCreationRollbacker;
	@Autowired
	private UpdatePasswordRequestRollbacker updatePasswordRequestRollbacker;
	@Autowired
	private UserPasswordUpdateRollbacker userPasswordUpdateRollbacker;
	
	public Rollbacker getRollbacker(MailTypes mailType) {
		if(mailType.equals(MailTypes.SEND_PASSWORD)) {
			return userCreationRollbacker;
		} else if(mailType.equals(MailTypes.UPDATE_PASSWORD_REQUEST)) {
			return updatePasswordRequestRollbacker;
		} else if(mailType.equals(MailTypes.SEND_NEW_PASSWORD)) {
			return userPasswordUpdateRollbacker;
		}
		
		return new Rollbacker();
	}
}
