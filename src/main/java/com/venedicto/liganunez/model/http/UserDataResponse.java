package com.venedicto.liganunez.model.http;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDataResponse {
	private int id;
	private String name;
	private int age;
	private String email;
}