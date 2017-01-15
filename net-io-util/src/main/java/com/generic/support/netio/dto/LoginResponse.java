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
	private String remoteIp;
	private String remoteAgent;
	private List<String> roles = new ArrayList<String>();
	private List<String> permissions = new ArrayList<String>();

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
	
	

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public String getRemoteAgent() {
		return remoteAgent;
	}

	public void setRemoteAgent(String remoteAgent) {
		this.remoteAgent = remoteAgent;
	}

	public List<String> getPermissions() {
		return Collections.unmodifiableList(permissions);
	}

	public void setPermissions(List<String> permissions) {
		this.permissions.addAll(permissions);
	}

	@Override
	public String toString() {
		return "LoginResponse [userid=" + userid + ", username=" + username + ", clientIdentity=" + clientIdentity
				+ ", serverIdentity=" + serverIdentity + ", remoteIp=" + remoteIp + ", remoteAgent=" + remoteAgent
				+ ", roles=" + roles + ", permissions=" + permissions + "]";
	}

}
