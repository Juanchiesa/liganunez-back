package com.venedicto.liganunez.model;

import lombok.Getter;

@Getter
public enum ErrorCodes {
	LN0000("LN", "0000", "Ocurrió un error insesperado"),
	LN0001("LN", "0001", "Formato de correo electrónico incorrecto"),
	LN0002("LN", "0002", "No se pudieron cargar los datos");
	
	private String id;
	private String code;
	private String description;
	
	ErrorCodes(String id, String code, String description) {
		this.id = id;
		this.code = code;
		this.description = description;
	}
}
