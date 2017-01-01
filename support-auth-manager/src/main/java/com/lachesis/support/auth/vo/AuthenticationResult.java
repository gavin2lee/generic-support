package com.lachesis.support.auth.vo;

public class AuthenticationResult {
	private String tokenValue;
	private String userId;

	public AuthenticationResult(String tokenValue, String userId) {
		super();
		this.tokenValue = tokenValue;
		this.userId = userId;
	}

	public String getTokenValue() {
		return tokenValue;
	}

	public String getUserId() {
		return userId;
	}

}
