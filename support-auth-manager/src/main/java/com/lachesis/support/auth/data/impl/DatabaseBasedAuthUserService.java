package com.lachesis.support.auth.data.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lachesis.support.auth.data.AuthUserService;
import com.lachesis.support.auth.model.AuthPermission;
import com.lachesis.support.auth.model.AuthRole;
import com.lachesis.support.auth.model.AuthUser;
import com.lachesis.support.auth.repository.AuthPermissionRepository;
import com.lachesis.support.auth.repository.AuthRoleRepository;
import com.lachesis.support.auth.repository.AuthUserRepository;

@Service("databaseBasedAuthUserService")
public class DatabaseBasedAuthUserService implements AuthUserService {
	private static final Logger LOG = LoggerFactory.getLogger(DatabaseBasedAuthUserService.class);

	@Autowired
	private AuthUserRepository userRepo;

	@Autowired
	private AuthRoleRepository roleRepo;

	@Autowired
	private AuthPermissionRepository permissionRepo;

	public AuthUser findAuthUserByUsername(String username) {
		if (StringUtils.isBlank(username)) {
			LOG.error("userid should be specified");
			throw new IllegalArgumentException("userid is empty");
		}

		AuthUser user = userRepo.findByUsername(username);
		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public AuthUser findAuthorizationInfoByUsername(String username) {
		if (StringUtils.isBlank(username)) {
			LOG.error("userid should be specified");
			throw new IllegalArgumentException("userid is empty");
		}

		AuthUser user = userRepo.findByUsername(username);
		if (user == null) {
			return null;
		}

		List<AuthRole> roles = roleRepo.findByUserId(user.getId());
		if ((roles == null) || roles.isEmpty()) {
			return user;
		}
		
		List<Long> roleIds = getRoleIds(roles);
		List<AuthPermission> permissions = permissionRepo.findByRoleIds(roleIds);
		
		user.setRoles(roles);
		user.setPermissions(permissions);
		return user;
	}

	private List<Long> getRoleIds(List<AuthRole> roles) {
		List<Long> roleIds = new ArrayList<Long>();
		for (AuthRole role : roles) {
			roleIds.add(role.getId());
		}
		
		return roleIds;
	}

}
