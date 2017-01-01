package com.lachesis.support.auth.context.vo;

public class UserDetailVO {
	private String userId;
	private String username;
	
	public UserDetailVO(String userId, String username) {
		super();
		this.userId = userId;
		this.username = username;
	}
	public String getUserId() {
		return userId;
	}
	
	public String getUsername() {
		return username;
	}
	@Override
	public String toString() {
		return "UserDetailVO [userId=" + userId + ", username=" + username + "]";
	}
}
