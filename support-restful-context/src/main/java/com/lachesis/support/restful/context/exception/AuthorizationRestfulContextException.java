package com.lachesis.support.restful.context.exception;

public class AuthorizationRestfulContextException extends RestfulContextException {

	private static final long serialVersionUID = -5693612746232484626L;

	public AuthorizationRestfulContextException() {
		super();
	}

	public AuthorizationRestfulContextException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AuthorizationRestfulContextException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthorizationRestfulContextException(String message) {
		super(message);
	}

	public AuthorizationRestfulContextException(Throwable cause) {
		super(cause);
	}

}
