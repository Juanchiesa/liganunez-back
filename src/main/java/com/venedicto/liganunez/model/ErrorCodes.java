package com.venedicto.liganunez.model;

import lombok.Getter;

@Getter
public enum ErrorCodes {
	LN0000("LN", "0000", "Ocurrió un error inesperado"),
	LN0001("LN", "0001", "Formato de correo electrónico incorrecto"),
	LN0002("LN", "0002", "No se pudieron cargar los datos"),
	LN0003("LN", "0003", "El campo 'barrio' no puede estar vacío"),
	LN0004("LN", "0004", "El formato del campo 'edad' es incorrecto"),
	LN0005("LN", "0005", "El campo 'nombre' no puede estar vacío"),
	LN0006("LN", "0006", "El usuario ya se encuentra registrado"),
	LN0007("LN", "0007", "El campo 'contraseña' no puede estar vacío"),
	LN0008("LN", "0008", "El usuario no se encuentra registrado"),
	LN0009("LN", "0009", "Clave incorrecta"),
	LN0010("LN", "0010", "Ya se generó una solicitud para actualizar la contraseña"),
	LN0011("LN", "0011", "El código de solicitud no puede estar vacío"),
	LN0012("LN", "0012", "El código de solicitud ingresado es inexistente"),
	LN0013("LN", "0013", "El código de solicitud está vencido"),
	LN0014("LN", "0014", "El torneo ingresado es inexistente"),
	LN0015("LN", "0015", "Acceso no autorizado"),
	LN0016("LN", "0016", "Sesión expirada"),
	LN0017("LN", "0017", "No se pudo encontrar la imagen"),
	LN0018("LN", "0018", "El campo 'fecha' no puede estar vacío"),
	LN0019("LN", "0019", "El formato del campo 'fecha' es incorrecto"),
	LN0020("LN", "0020", "El campo 'sede' no puede estar vacío"),
	LN0021("LN", "0021", "El campo 'torneo' no puede estar vacío"),
	LN0022("LN", "0022", "El torneo ingresado no existe"),
	LN0023("LN", "0023", "La imagen es inexistente");
	
	private String id;
	private String code;
	private String description;
	
	ErrorCodes(String id, String code, String description) {
		this.id = id;
		this.code = code;
		this.description = description;
	}
}
