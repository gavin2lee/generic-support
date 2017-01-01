package com.lachesis.support.auth.data;

import java.util.List;

import com.lachesis.support.objects.entity.auth.Permission;
import com.lachesis.support.objects.entity.auth.Role;

public interface RoleService {
	Role saveRole(Role r);
	
	Role updateRole(Role r);

	Role removeRole(Role r);

	Role findRoleById(Long id);

	Role findRoleByName(String name);

	List<Role> findAll();

	Role addPermission(Role role, Permission perm);

	Role deletePermission(Role role, Permission perm);
}
