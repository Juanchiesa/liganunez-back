package com.venedicto.liganunez.model;

import lombok.Getter;

@Getter
public enum MailTypes {
	ACCOUNT_CREATED("[Liga Nuñez] Tu clave de acceso", "registration"),
	UPDATE_PASSWORD_REQUEST("[Liga Nuñez] Confirmar cambio de clave", "registration");
	
	private String subject;
	private String template;
	
	MailTypes(String type, String template) {
		this.subject = type;
		this.template = template;
	}
}
