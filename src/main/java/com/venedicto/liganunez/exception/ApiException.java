package com.venedicto.liganunez.exception;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-06-18T18:56:59.203396144Z[GMT]")
public class ApiException extends Exception {
    private static final long serialVersionUID = 460519011014667959L;
	@SuppressWarnings("unused")
	private int code;
    public ApiException (int code, String msg) {
        super(msg);
        this.code = code;
    }
}
