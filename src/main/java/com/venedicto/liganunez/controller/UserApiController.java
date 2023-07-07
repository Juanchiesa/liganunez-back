package com.venedicto.liganunez.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.venedicto.liganunez.api.UserApi;
import com.venedicto.liganunez.handler.UserApiHandler;
import com.venedicto.liganunez.model.http.Error;
import com.venedicto.liganunez.model.http.HttpResponse;
import com.venedicto.liganunez.model.http.User;
import com.venedicto.liganunez.model.http.UserLoginHttpResponse;
import com.venedicto.liganunez.utils.HttpUtils;
import com.venedicto.liganunez.validator.UserValidator;

@RestController
public class UserApiController implements UserApi {
    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);
    @Autowired
    private UserApiHandler handler;
    @Autowired
    private UserValidator validator;
    
    public ResponseEntity<HttpResponse> checkUserExistence(String email) {
    	HttpResponse response = new HttpResponse();
    	
    	List<Error> errors = validator.validateEmail(email);
    	if(!errors.isEmpty()) {
    		return HttpUtils.badRequestResponse(log, response, errors);
    	}
    	
    	log.info("[Check user] Se recibió una solicitud para el correo {}", email);
        return handler.checkUserExistence(response, email);
    }

    public ResponseEntity<HttpResponse> createUser(User body) {
    	HttpResponse response = new HttpResponse();
    	
    	List<Error> errors = validator.validateUser(body);
    	if(!errors.isEmpty()) {
    		return HttpUtils.badRequestResponse(log, response, errors);
    	}
    	
        return handler.createUser(response, body);
    }

    public ResponseEntity<UserLoginHttpResponse> loginUser(String email, String password) {
        UserLoginHttpResponse response = new UserLoginHttpResponse();
        
        List<Error> errors = validator.validateLoginRequest(email, password);
        if(!errors.isEmpty()) {
        	return HttpUtils.badRequestResponse(log, response, errors);
        }
        
        log.info("[Login] Se recibió una solicitud de acceso de {}", email);
    	return handler.login(response, email, password);
    }

    public ResponseEntity<HttpResponse> requestUserPasswordUpdate(String userEmail) {
        HttpResponse response = new HttpResponse();
        
        List<Error> errors = validator.validateEmail(userEmail);
        if(!errors.isEmpty()) {
        	return HttpUtils.badRequestResponse(log, response, errors);
        }
        
        log.info("[Password update request] Se recibió una solicitud de {}", userEmail);
    	return handler.requestUserPasswordUpdate(response, userEmail);
    }

    public ResponseEntity<HttpResponse> updateUserPassword(String requestCode) {
    	HttpResponse response = new HttpResponse();
    	
    	List<Error> errors = validator.validatePasswordUpdateRequest(requestCode);
    	if(!errors.isEmpty()) {
    		return HttpUtils.badRequestResponse(log, response, errors);
    	}
    	
    	log.info("[Password update] Se recibió una solicitud con el código {}", requestCode);
        return handler.updateUserPassword(response, requestCode);
    }

	public ResponseEntity<HttpResponse> downloadPicture(String pictureId, String token) {
		HttpResponse response = new HttpResponse();
		
		log.info("[Register download] Se recibió una solicitud para descargar la imagen {}", pictureId);
		return handler.registerDownload(response, token, pictureId);
	}
}