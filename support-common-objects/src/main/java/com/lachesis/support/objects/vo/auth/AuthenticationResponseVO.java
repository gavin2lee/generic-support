package com.lachesis.support.objects.vo.auth;

public class AuthenticationResponseVO {
	private String token;
	private String userId;

	public AuthenticationResponseVO() {
		super();
	}
	public AuthenticationResponseVO(String token, String userId) {
		super();
		this.token = token;
		this.userId = userId;
	}
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
	@Override
	public String toString() {
		return "AuthenticationResponseVO [token=" + token + ", userId=" + userId + "]";
	}
}
