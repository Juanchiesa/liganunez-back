package com.venedicto.liganunez.model;

import lombok.Getter;

@Getter
public enum ErrorCodes {
	LN0000("LN", "0000", "Ocurrió un error insesperado"),
	LN0001("LN", "0001", "Formato de correo electrónico incorrecto"),
	LN0002("LN", "0002", "No se pudieron cargar los datos"),
	LN0003("LN", "0003", "El campo 'barrio' no puede estar vacío"),
	LN0004("LN", "0004", "El formato del campo 'edad' es incorrecto"),
	LN0005("LN", "0005", "El campo 'nombre' no puede estar vacío"),
	LN0006("LN", "0006", "El usuario ya se encuentra registrado");
	
	private String id;
	private String code;
	private String description;
	
	ErrorCodes(String id, String code, String description) {
		this.id = id;
		this.code = code;
		this.description = description;
	}
}
