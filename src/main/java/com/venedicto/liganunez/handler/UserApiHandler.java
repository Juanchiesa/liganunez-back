package com.venedicto.liganunez.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.venedicto.liganunez.service.UserService;

@Component
public class UserApiHandler {
	@Autowired
	private UserService userService;
}
