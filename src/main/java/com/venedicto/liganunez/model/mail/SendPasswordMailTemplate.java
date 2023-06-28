package com.venedicto.liganunez.model.mail;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SendPasswordMailTemplate {
	private String accessKey;
	@JsonIgnore
	private String oldAccessKey;
}