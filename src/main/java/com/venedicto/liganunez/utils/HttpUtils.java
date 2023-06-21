package com.venedicto.liganunez.utils;

import com.venedicto.liganunez.model.ErrorCodes;
import com.venedicto.liganunez.model.http.Error;

public class HttpUtils {
	public static Error generateError(ErrorCodes errorCode) {
		Error error = new Error();
		error.setCode("["+errorCode.getId()+"]"+errorCode.getCode());
		error.setDescription(errorCode.getDescription());
		return error;
	}
}