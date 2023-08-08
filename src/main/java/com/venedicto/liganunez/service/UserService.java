package com.venedicto.liganunez.service;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venedicto.liganunez.exception.RequestExpiredException;
import com.venedicto.liganunez.model.MailTypes;
import com.venedicto.liganunez.model.PasswordUpdateRequest;
import com.venedicto.liganunez.model.UserData;
import com.venedicto.liganunez.model.http.User;
import com.venedicto.liganunez.model.mail.SendPasswordMailTemplate;
import com.venedicto.liganunez.model.mail.PasswordUpdateRequestMailTemplate;
import com.venedicto.liganunez.repository.UserRepository;
import com.venedicto.liganunez.service.external.MailSenderService;
import com.venedicto.liganunez.utils.NumberUtils;

@Service
public class UserService {
	private Logger log = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private AuthService authService;
	@Autowired
	private MailSenderService mailService;
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
		
		SendPasswordMailTemplate mailData = new SendPasswordMailTemplate();
		mailData.setAccessKey(password);
		mailService.sendMail(user.getEmail(), MailTypes.SEND_PASSWORD, mailData);
	}
	
	public UserData login(String email, String password) throws LoginException {
		UserData userData = userRepository.getUser(email);
		
		if(!authService.passwordMatches(password, userData.getData().getAccessKey())) {
			throw new LoginException("Clave incorrecta");
		}
		userData.getData().setAccessKey(null);
		
		return userData;
	}
	
	public void generatePasswordUpdateRequest(String email) {
		String code = UUID.randomUUID().toString();
		log.debug("[Password update request] Generado el código de recuperación {}", code);
		
		userRepository.createPasswordUpdateRequest(email, code);
		
		PasswordUpdateRequestMailTemplate mailData = new PasswordUpdateRequestMailTemplate();
		mailData.setRequestCode(code);
		mailService.sendMail(email, MailTypes.UPDATE_PASSWORD_REQUEST, mailData);
	}
	
	public String getPasswordUpdateRequest(String requestCode) {
		PasswordUpdateRequest request = userRepository.getPasswordUpdateRequest(requestCode);
		
		if(LocalDateTime.now().isAfter(request.getCreationDate().plusMinutes(30))) {
			userRepository.deletePasswordUpdateRequest(requestCode);
			throw new RequestExpiredException("Solicitud vencida");
		}
		
		return request.getUserEmail();
	}
	
	public void generateNewPassword(String requestCode, String userEmail) {
		UserData user = userRepository.getUser(userEmail);
		
		String password = NumberUtils.generateRandomNumber(6);
		String encodedPassword = authService.encodePassword(password);
		log.debug("[Password update] Password asignada correctamente");
		
		userRepository.updateUserPassword(userEmail, encodedPassword);
		
		SendPasswordMailTemplate mailData = new SendPasswordMailTemplate();
		mailData.setAccessKey(password);
		mailData.setOldAccessKey(user.getData().getAccessKey());
		mailService.sendMail(userEmail, MailTypes.SEND_NEW_PASSWORD, mailData);
		
		userRepository.deletePasswordUpdateRequest(requestCode);
	}
	
	public void registerDownload(String pictureId, String userId) {
		userRepository.createDownload(pictureId, userId);
	}
	
	public int getUsersStats() {
		return userRepository.getUsersStats();
	}
}