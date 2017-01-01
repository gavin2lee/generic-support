package com.lachesis.support.auth.service.impl;

import java.util.Date;

import com.lachesis.support.objects.entity.auth.Token;

class AuthTokenGenerator {
	private String username;
	private String password;
	private String terminalIpAddress;
	private String tokenValue;
	
	public AuthTokenGenerator(String username, String password, String terminalIpAddress,String tokenValue){
		this.username = username;
		this.password = password;
		this.terminalIpAddress = terminalIpAddress;
		this.tokenValue = tokenValue;
	}
	
	public Token generate(){
		Token t = new Token();
		t.setActive(true);
		t.setLastModified(new Date());
		t.setPassword(password);
		t.setTerminalIp(terminalIpAddress);
		t.setUsername(username);
		t.setTokenValue(tokenValue);
		
		return t;
	}
}
