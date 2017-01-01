package com.lachesis.support.auth.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class AuthUser {
	private Long id;
	private String code;
	private String username;
	private String password;

	private String name;
	private String gender;

	private String mobilePhone;
	private String telephone;
	private String email;

	private Collection<AuthRole> roles = new HashSet<AuthRole>();

	private Collection<AuthPermission> permissions = new HashSet<AuthPermission>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<AuthRole> getRoles() {
		return Collections.unmodifiableCollection(roles);
	}

	public void addRole(AuthRole role) {
		if (role != null) {
			this.roles.add(role);
		}
	}

	public void setRoles(Collection<AuthRole> roles) {
		if (roles != null) {
			this.roles.addAll(roles);
		}
	}

	public Collection<AuthPermission> getPermissions() {
		return Collections.unmodifiableCollection(permissions);
	}

	public void addPermission(AuthPermission permission) {
		this.permissions.add(permission);
	}

	public void setPermissions(Collection<AuthPermission> permissions) {
		if (permissions != null) {
			this.permissions.addAll(permissions);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AuthUser other = (AuthUser) obj;
		if (code == null) {
			if (other.code != null) {
				return false;
			}
		} else if (!code.equals(other.code)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}

}
