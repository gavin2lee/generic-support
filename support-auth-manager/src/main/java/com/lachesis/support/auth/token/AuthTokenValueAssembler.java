package com.lachesis.support.auth.token;

import com.lachesis.support.auth.common.AuthConstants;

public class AuthTokenValueAssembler {
	private String terminalIpAdress;
	private String username;
	public AuthTokenValueAssembler( String username , String terminalIpAdress) {
		super();
		this.terminalIpAdress = terminalIpAdress;
		this.username = username;
	}
	
	public String buildTokenValue(){
		StringBuilder sb = new StringBuilder();
		sb.append(username);
		sb.append(AuthConstants.TOKEN_PARTS_DELIMITER);
		sb.append(terminalIpAdress);
		sb.append(AuthConstants.TOKEN_PARTS_DELIMITER);
		sb.append(System.nanoTime());
		
		return sb.toString();
	}
}
