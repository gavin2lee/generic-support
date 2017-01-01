package com.lachesis.support.auth.token;

import java.util.LinkedList;
import java.util.List;

import com.lachesis.support.auth.common.AuthConstants;

public class AuthTokenValueParser {
	private String plainTokenValue;
	private String terminalIpAdress;
	private String userid;

	public AuthTokenValueParser(String plainTokenValue) {
		super();
		this.plainTokenValue = plainTokenValue;
		String [] parts = splitTokenValue(plainTokenValue);
		
		if(parts.length != 3){
			throw new RuntimeException("token should consist of 3 parts but "+parts.length);
		}
		
		userid = parts[0];
		terminalIpAdress = parts[1];
	}
	
	private String [] splitTokenValue(String tokenValue){
		String delimiter = AuthConstants.TOKEN_PARTS_DELIMITER;
		List<String> parts = new LinkedList<String>();
		int index = -1;
		while((index = tokenValue.indexOf(delimiter)) != -1){
			String part = tokenValue.substring(0, index);
			parts.add(part);
			tokenValue = tokenValue.substring(index+1);
		}
		
		parts.add(tokenValue);
		
		return parts.toArray(new String[parts.size()]);
	}
	
	public String getTerminalIpAddress(){
		return terminalIpAdress;
	}
	
	public String getUserid(){
		return userid;
	}
	
	public String getTokenValue(){
		return this.plainTokenValue;
	}
}
