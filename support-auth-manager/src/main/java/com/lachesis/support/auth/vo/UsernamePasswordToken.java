package com.lachesis.support.auth.vo;

public class UsernamePasswordToken {
	private String username;
	private String password;
	
	public UsernamePasswordToken(String username,String password){
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
}
