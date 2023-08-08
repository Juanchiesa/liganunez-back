package com.venedicto.liganunez.model;

import lombok.Getter;

@Getter
public enum MailTypes {
	SEND_PASSWORD("Liga Nuñez - Tu clave de acceso", "registration"),
	SEND_NEW_PASSWORD("Liga Nuñez - Tu nueva clave de acceso", "registration"),
	UPDATE_PASSWORD_REQUEST("Liga Nuñez - Confirmar cambio de clave", "reset-password");
	
	private String subject;
	private String template;
	
	MailTypes(String type, String template) {
		this.subject = type;
		this.template = template;
	}
}
