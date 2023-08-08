package com.venedicto.liganunez.validator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.venedicto.liganunez.model.ErrorCodes;
import com.venedicto.liganunez.model.http.Error;
import com.venedicto.liganunez.utils.HttpUtils;

@Component
public class PictureValidator extends Validator {
	public List<Error> validateFileUpload(String tournamentId, String place, String date) {
		List<Error> errors = new ArrayList<>();
		
		if(stringNullOrEmpty(date)) {
			errors.add(HttpUtils.generateError(ErrorCodes.LN0018));
		} else {
			errors.addAll(validateDateFormat(date));
		}
		
		if(stringNullOrEmpty(place)) {
			errors.add(HttpUtils.generateError(ErrorCodes.LN0020));
		}
		
		if(stringNullOrEmpty(tournamentId)) {
			errors.add(HttpUtils.generateError(ErrorCodes.LN0021));
		}
		
		return errors;
	}
	
	public List<Error> validateDateFormat(String date) {
		List<Error> errors = new ArrayList<>();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		
		try {
			dateFormat.parse(date);
		} catch(Exception e) {
			errors.add(HttpUtils.generateError(ErrorCodes.LN0019));
		}
		
		return errors;
	}
}