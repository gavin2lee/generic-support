package com.lachesis.support.objects.vo.auth;

import java.util.ArrayList;
import java.util.Collection;

public class AuthorizationResponseVO {
	private String userId;
	private String username;
	private Collection<String> roles = new ArrayList<String>();
	private Collection<String> permissions = new ArrayList<String>();

	public AuthorizationResponseVO() {
		super();
	}

	public AuthorizationResponseVO(String userId, String username) {
		super();
		this.userId = userId;
		this.username = username;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String id) {
		this.userId = id;
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
			this.roles.clear();
			this.roles.addAll(roles);
		}
	}

	public Collection<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Collection<String> permissions) {
		if (permissions != null) {
			this.permissions.clear();
			this.permissions.addAll(permissions);
		}
	}

	@Override
	public String toString() {
		return "AuthorizationResponseVO [id=" + userId + ", username=" + username + ", roles=" + roles + ", permissions="
				+ permissions + "]";
	}
}
