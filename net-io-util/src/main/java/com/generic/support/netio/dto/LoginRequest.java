package com.generic.support.netio.dto;

import org.msgpack.annotation.Message;

@Message
public class LoginRequest {
	private String username;
	private String password;
	
	private String clientIdentity;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getClientIdentity() {
		return clientIdentity;
	}

	public void setClientIdentity(String clientIdentity) {
		this.clientIdentity = clientIdentity;
	}

	@Override
	public String toString() {
		return "LoginRequest [username=" + username + ", password=" + password + ", clientIdentity=" + clientIdentity
				+ "]";
	}

}
