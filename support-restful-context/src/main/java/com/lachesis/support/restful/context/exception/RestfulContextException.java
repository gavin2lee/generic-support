package com.lachesis.support.restful.context.exception;

public class RestfulContextException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RestfulContextException() {
		super();
	}

	public RestfulContextException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RestfulContextException(String message, Throwable cause) {
		super(message, cause);
	}

	public RestfulContextException(String message) {
		super(message);
	}

	public RestfulContextException(Throwable cause) {
		super(cause);
	}
	
	

}
