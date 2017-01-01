package com.lachesis.support.auth.api.vo;

public class AuthenticationResponse implements AuthResponse{
	private String token;
	private String userId;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
