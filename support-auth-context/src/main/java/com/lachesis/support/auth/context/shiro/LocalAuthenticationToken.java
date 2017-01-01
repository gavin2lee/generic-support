package com.lachesis.support.auth.context.shiro;

import java.util.Map;

import org.apache.shiro.authc.AuthenticationToken;

public class LocalAuthenticationToken implements AuthenticationToken {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8022840809330562105L;
	private String token;
	private String terminalIpAddress;
	private Map<String,?> params;
	
	public LocalAuthenticationToken(String token, String terminalIpAddress, Map<String, ?> params) {
		super();
		this.token = token;
		this.terminalIpAddress = terminalIpAddress;
		this.params = params;
	}
	
	public String getToken() {
		return token;
	}

	public String getTerminalIpAddress() {
		return terminalIpAddress;
	}

	public Map<String, ?> getParams() {
		return params;
	}

	@Override
	public Object getPrincipal() {
		
		return token;
	}

	@Override
	public Object getCredentials() {
		return terminalIpAddress;
	}

}
