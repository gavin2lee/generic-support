package com.lachesis.support.auth.api.common;

public final class AuthBizErrorCodes {
	private AuthBizErrorCodes(){}
	
	public static final String DEFAULT = "400.000";
	public static final String AUTH_FAILED = "401.001";
	public static final String AUTH_FAILED_ARGS = "401.002";
	public static final String AUTH_FAILED_TOKEN = "401.003";
}