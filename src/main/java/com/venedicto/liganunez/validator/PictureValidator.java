package com.venedicto.liganunez.validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.venedicto.liganunez.model.http.Picture;

@Component
public class PictureValidator extends Validator {
	public boolean isValidFile(Picture picture) {
		if(stringNullOrEmpty(picture.getTournamentId())) {
			return false;
		}
		
		if(stringNullOrEmpty(picture.getDate())) {
			return false;
		} else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			try {
				LocalDateTime.parse(picture.getDate(), formatter);
			} catch(Exception e) {
				return false;
			}
		}
		
		if(stringNullOrEmpty(picture.getPlace())) {
			return false;
		}
		
		if(stringNullOrEmpty(picture.getFile())) {
			return false;
		}
		
		return true;
	}
}