package com.venedicto.liganunez.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.venedicto.liganunez.model.ErrorCodes;
import com.venedicto.liganunez.model.http.Error;
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
}
