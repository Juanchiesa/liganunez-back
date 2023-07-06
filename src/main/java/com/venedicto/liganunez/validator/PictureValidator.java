package com.venedicto.liganunez.validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.venedicto.liganunez.model.ErrorCodes;
import com.venedicto.liganunez.model.http.Error;
import com.venedicto.liganunez.utils.HttpUtils;

import org.springframework.stereotype.Component;

@Component
public class PictureValidator extends Validator {
	public List<Error> validateFileUpload(String tournamentId, String place, String date) {
		List<Error> errors = new ArrayList<>();
		
		if(stringNullOrEmpty(date)) {
			errors.add(HttpUtils.generateError(ErrorCodes.LN0018));
		} else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			try {
				LocalDateTime.parse(date, formatter);
			} catch(Exception e) {
				errors.add(HttpUtils.generateError(ErrorCodes.LN0019));
			}
		}
		
		if(stringNullOrEmpty(place)) {
			errors.add(HttpUtils.generateError(ErrorCodes.LN0020));
		}
		
		if(stringNullOrEmpty(tournamentId)) {
			errors.add(HttpUtils.generateError(ErrorCodes.LN0021));
		}
		
		return errors;
	}
}