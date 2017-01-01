package com.lachesis.support.auth.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SimpleAuthorizationResult implements AuthorizationResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4072057897019711567L;
	private String id;
	private String username;
	private String password;
	private Collection<String> roles = new ArrayList<String>();
	private Collection<String> permissions = new ArrayList<String>();
	
	public SimpleAuthorizationResult(String id, String userid, String password) {
		this(id,userid,password,null,null);
	}
	
	public SimpleAuthorizationResult(String id, String userid, String password, Collection<String> roles) {
		this(id,userid,password,roles,null);
	}
	
	public SimpleAuthorizationResult(String id, String userid, String password, Collection<String> roles,
			Collection<String> permissions) {
		this.id = id;
		this.username = userid;
		this.password = password;
		if(roles!=null){
			this.roles.addAll(roles);
		}
		if(permissions!=null){
			this.permissions.addAll(permissions);
		}
	}

	@Override
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public Collection<String> getRoles() {
		return Collections.unmodifiableCollection(this.roles);
	}

	@Override
	public Collection<String> getPermissions() {
		return Collections.unmodifiableCollection(this.permissions);
	}

}
