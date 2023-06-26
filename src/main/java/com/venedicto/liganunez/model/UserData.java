package com.venedicto.liganunez.model;

import com.venedicto.liganunez.model.http.User;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserData {
	private String id;
	private User data;
	private String creationDate;
	private String lastUpdateDate;
}
