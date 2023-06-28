package com.venedicto.liganunez.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PasswordUpdateRequest {
	private String id;
	private String userEmail;
	private LocalDateTime creationDate;
}