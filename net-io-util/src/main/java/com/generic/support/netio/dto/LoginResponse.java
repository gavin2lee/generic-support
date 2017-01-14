package com.generic.support.netio.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.msgpack.annotation.Message;

@Message
public class LoginResponse {
	private long userid;
	private String username;
	private String clientIdentity;
	private String serverIdentity;
	private List<String> roles = new ArrayList<String>();

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getClientIdentity() {
		return clientIdentity;
	}

	public void setClientIdentity(String clientIdentity) {
		this.clientIdentity = clientIdentity;
	}

	public String getServerIdentity() {
		return serverIdentity;
	}

	public void setServerIdentity(String serverIdentity) {
		this.serverIdentity = serverIdentity;
	}

	public List<String> getRoles() {
		return Collections.unmodifiableList(roles);
	}

	public void setRoles(List<String> roles) {
		this.roles.addAll(roles);
	}

	@Override
	public String toString() {
		return "LoginResponse [userid=" + userid + ", username=" + username + ", clientIdentity=" + clientIdentity
				+ ", serverIdentity=" + serverIdentity + ", roles=" + roles + "]";
	}

}
