package com.lachesis.support.auth.context.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class AuthorizationInfoVO {
	private UserDetailVO user;
	private Collection<String> roles = new ArrayList<String>();
	private Collection<String> permissions = new ArrayList<String>();

	public AuthorizationInfoVO(UserDetailVO user, Collection<String> roles, Collection<String> permissions) {
		super();
		this.user = user;
		if (roles != null) {
			this.roles.addAll(roles);
		}
		if (permissions != null) {
			this.permissions.addAll(permissions);
		}
	}

	public UserDetailVO getUser() {
		return user;
	}

	public Collection<String> getRoles() {
		return Collections.unmodifiableCollection(roles);
	}

	public Collection<String> getPermissions() {
		return Collections.unmodifiableCollection(permissions);
	}

	@Override
	public String toString() {
		return "AuthorizationInfoVO [user=" + user + ", roles=" + roles + ", permissions=" + permissions + "]";
	}

	
}
