package com.venedicto.liganunez.exception;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-06-18T18:56:59.203396144Z[GMT]")
public class NotFoundException extends ApiException {
    private static final long serialVersionUID = -4002027121029902268L;
	@SuppressWarnings("unused")
	private int code;
    public NotFoundException (int code, String msg) {
        super(code, msg);
        this.code = code;
    }
}
