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
		
		if(!RegExpUtils.isValidEmail(email)) {
			errors.add(HttpUtils.generateError(ErrorCodes.LN0001));
		}
		
		return errors;
	}
	
	private boolean stringNullOrEmpty(String value) {
		return value == null || value.isEmpty();
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
}
