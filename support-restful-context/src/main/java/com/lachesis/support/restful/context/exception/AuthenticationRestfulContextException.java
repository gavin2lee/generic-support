package com.lachesis.support.restful.context.exception;

public class AuthenticationRestfulContextException extends RestfulContextException {

	private static final long serialVersionUID = 7739367053301271441L;

	public AuthenticationRestfulContextException() {
		super();
	}

	public AuthenticationRestfulContextException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AuthenticationRestfulContextException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthenticationRestfulContextException(String message) {
		super(message);
	}

	public AuthenticationRestfulContextException(Throwable cause) {
		super(cause);
	}

}
