package com.venedicto.liganunez.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.venedicto.liganunez.api.UserApi;
import com.venedicto.liganunez.handler.UserApiHandler;
import com.venedicto.liganunez.model.HttpResponse;
import com.venedicto.liganunez.model.User;
import com.venedicto.liganunez.model.UserLoginHttpResponse;
import com.venedicto.liganunez.validator.UserValidator;

@RestController
public class UserApiController implements UserApi {
    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);
    @Autowired
    private UserApiHandler handler;
    @Autowired
    private UserValidator validator;
    
    public ResponseEntity<HttpResponse> checkUserExistence(String email) {
        return new ResponseEntity<HttpResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<HttpResponse> createUser(User body) {
        return new ResponseEntity<HttpResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<UserLoginHttpResponse> loginUser(String email, String password) {
        return new ResponseEntity<UserLoginHttpResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<HttpResponse> logoutUser(String email) {
        return new ResponseEntity<HttpResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<HttpResponse> requestUserPasswordUpdate() {
        return new ResponseEntity<HttpResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<HttpResponse> updateUserPassword(String newPassword, String authCode) {
        return new ResponseEntity<HttpResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<HttpResponse> updateUserPermissionLevel(Integer permissionLevel, String token) {
        return new ResponseEntity<HttpResponse>(HttpStatus.NOT_IMPLEMENTED);
    }
}