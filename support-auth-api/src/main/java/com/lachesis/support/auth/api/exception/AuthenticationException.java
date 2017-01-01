package com.lachesis.support.auth.api.exception;

public class AuthenticationException extends RuntimeException {
	
	private static final long serialVersionUID = -2774946159397284137L;
	
	private String code;
	
	public AuthenticationException(String code, String msg) {
		super(msg);
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
