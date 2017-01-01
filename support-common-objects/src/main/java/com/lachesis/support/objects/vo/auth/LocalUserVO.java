package com.lachesis.support.objects.vo.auth;

import java.util.ArrayList;
import java.util.Collection;

public class LocalUserVO{
	private String id;
	private String username;
	private String password;
	private Collection<String> roles = new ArrayList<String>();
	private Collection<String> permissions = new ArrayList<String>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Collection<String> getRoles() {
		return roles;
	}

	public void setRoles(Collection<String> roles) {
		if (roles != null) {
			this.roles.clear();
			this.roles.addAll(roles);
		}
	}

	public Collection<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Collection<String> permissions) {
		if(permissions != null){
			this.permissions.clear();
			this.permissions.addAll(permissions);
		}
	}
}
