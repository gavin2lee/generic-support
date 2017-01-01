package com.lachesis.support.common.util.exception;

public class CryptException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -179490973501705932L;

	public CryptException() {
		super();
	}

	public CryptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CryptException(String message, Throwable cause) {
		super(message, cause);
	}

	public CryptException(String message) {
		super(message);
	}

	public CryptException(Throwable cause) {
		super(cause);
	}

}
