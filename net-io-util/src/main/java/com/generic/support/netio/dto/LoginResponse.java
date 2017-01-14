package com.generic.support.netio.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoginResponse {
	private long userid;
	private String username;
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

	public List<String> getRoles() {
		return Collections.unmodifiableList(roles);
	}

	public void setRoles(List<String> roles) {
		this.roles.addAll(roles);
	}

}
