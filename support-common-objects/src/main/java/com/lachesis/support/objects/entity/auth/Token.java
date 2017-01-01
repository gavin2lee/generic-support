package com.lachesis.support.objects.entity.auth;

import java.io.Serializable;
import java.util.Date;

public class Token implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4247871662305154446L;
	private String tokenValue;
	private String terminalIp;
	private Boolean active;
	private Date lastModified;
	private String username;
	private String password;

	public String getTokenValue() {
		return tokenValue;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

	public String getTerminalIp() {
		return terminalIp;
	}

	public void setTerminalIp(String terminalIpAddress) {
		this.terminalIp = terminalIpAddress;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

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

	@Override
	public String toString() {
		return "Token [tokenValue=" + tokenValue + ", terminalIp=" + terminalIp + ", active=" + active
				+ ", lastModified=" + lastModified + ", username=" + username + ", password="
				+ (password == null ? "null" : "*") + "]";
	}

}
