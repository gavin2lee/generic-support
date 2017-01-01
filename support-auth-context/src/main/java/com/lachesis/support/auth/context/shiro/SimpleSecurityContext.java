package com.lachesis.support.auth.context.shiro;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import com.lachesis.support.objects.vo.SecurityContext;

class SimpleSecurityContext implements SecurityContext {
	private String principal;
	private String token;
	private String terminalIpAddress;
	private Collection<String> roles = new LinkedList<String>();
	private Collection<String> permissions = new LinkedList<String>();

	public SimpleSecurityContext(String principal, String token, String terminalIpAddress, Collection<String> roles,
			Collection<String> permissions) {
		super();
		this.principal = principal;
		this.token = token;
		this.terminalIpAddress = terminalIpAddress;
		setRoles(roles);
		setPermissions(permissions);
	}

	public String getPrincipal() {
		return principal;
	}

	public String getToken() {
		return token;
	}

	public String getTerminalIpAddress() {
		return terminalIpAddress;
	}

	public Collection<String> getRoles() {
		return Collections.unmodifiableCollection(roles);
	}

	public Collection<String> getPermissions() {
		return Collections.unmodifiableCollection(permissions);
	}

	private void setRoles(Collection<String> roles) {
		if (roles != null) {
			this.roles.addAll(roles);
		}
	}

	private void setPermissions(Collection<String> permissions) {
		if (permissions != null) {
			this.permissions.addAll(permissions);
		}
	}

	@Override
	public String toString() {
		return "SecurityContext [principal=" + principal + ", token=" + token + ", terminalIpAddress="
				+ terminalIpAddress + ", roles=" + roles + ", permissions=" + permissions + "]";
	}

}
