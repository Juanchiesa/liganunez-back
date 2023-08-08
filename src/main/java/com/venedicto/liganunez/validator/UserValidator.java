package com.venedicto.liganunez.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.venedicto.liganunez.model.ErrorCodes;
import com.venedicto.liganunez.model.http.Error;
import com.venedicto.liganunez.model.http.User;
import com.venedicto.liganunez.utils.HttpUtils;
import com.venedicto.liganunez.utils.RegExpUtils;

@Component
public class UserValidator extends Validator {
	public List<Error> validateEmail(String email) {
		List<Error> errors = new ArrayList<>();
		
		if(stringNullOrEmpty(email) || !RegExpUtils.isValidEmail(email)) {
			errors.add(HttpUtils.generateError(ErrorCodes.LN0001));
		}
		
		return errors;
	}
	
	public List<Error> validateUser(User user) {
		List<Error> errors = new ArrayList<>();
		
		if(stringNullOrEmpty(user.getName())) {
			errors.add(HttpUtils.generateError(ErrorCodes.LN0005));
		}
		if(stringNullOrEmpty(user.getAddress())) {
			errors.add(HttpUtils.generateError(ErrorCodes.LN0003));
		}
		if(user.getAge() == null || user.getAge() <= 0) {
			errors.add(HttpUtils.generateError(ErrorCodes.LN0004));
		}
		errors.addAll(validateEmail(user.getEmail()));
		
		return errors;
	}
	
	public List<Error> validateLoginRequest(String email, String password) {
		List<Error> errors = new ArrayList<>();
		
		errors.addAll(validateEmail(email));
		if(stringNullOrEmpty(password)) {
			errors.add(HttpUtils.generateError(ErrorCodes.LN0007));
		}
		
		return errors;
	}
	
	public List<Error> validatePasswordUpdateRequest(String requestCode) {
		List<Error> errors = new ArrayList<>();
		
		if(stringNullOrEmpty(requestCode)) {
			errors.add(HttpUtils.generateError(ErrorCodes.LN0011));
		}
		
		return errors;
	}
}
