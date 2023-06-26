package com.venedicto.liganunez.service;

import java.util.UUID;

import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venedicto.liganunez.model.MailTypes;
import com.venedicto.liganunez.model.UserData;
import com.venedicto.liganunez.model.http.User;
import com.venedicto.liganunez.model.mail.AccountCreatedMailTemplate;
import com.venedicto.liganunez.repository.UserRepository;
import com.venedicto.liganunez.service.external.MailSender;
import com.venedicto.liganunez.utils.NumberUtils;

@Service
public class UserService {
	private Logger log = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private AuthService authService;
	@Autowired
	private MailSender mailService;
	@Autowired
	private UserRepository userRepository;
	
	public boolean userExists(String email) {
		return userRepository.checkUserExistence(email) == 1;
	}
	
	public void createUser(User user) {
		String id = UUID.randomUUID().toString();
		log.trace("[Create user] Asignado el ID {} al usuario {}", id, user.getEmail());
		
		String password = NumberUtils.generateRandomNumber(6);
		String encodedPassword = authService.encodePassword(password);
		log.debug("[Create user] Password asignada correctamente");
		
		userRepository.createUser(id, encodedPassword, user);
		
		AccountCreatedMailTemplate mailData = new AccountCreatedMailTemplate();
		mailData.setAccessKey(password);
		mailService.sendMail(user.getEmail(), MailTypes.ACCOUNT_CREATED, mailData);
	}
	
	public UserData login(String email, String password) throws LoginException {
		UserData userData = userRepository.getUser(email);
		
		if(!authService.passwordMatches(password, userData.getData().getAccessKey())) {
			throw new LoginException("Clave incorrecta");
		}
		userData.getData().setAccessKey(null);
		
		return userData;
	}
}