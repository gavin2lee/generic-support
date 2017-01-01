package com.lachesis.support.auth.api.vo;

import java.util.ArrayList;
import java.util.Collection;

public class AuthorizationResponse implements AuthResponse {
	private String id;
	private String username;
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

	public Collection<String> getRoles() {
		return roles;
	}

	public void setRoles(Collection<String> roles) {
		if (roles != null) {
			this.roles.addAll(roles);
		}
	}

	public Collection<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Collection<String> permissions) {
		if(permissions!=null){
			this.permissions.addAll(permissions);
		}
	}
}
