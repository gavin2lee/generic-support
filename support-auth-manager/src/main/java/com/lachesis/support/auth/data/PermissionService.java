package com.lachesis.support.auth.data;

import java.util.List;

import com.lachesis.support.objects.entity.auth.Permission;

public interface PermissionService {
	Permission savePermission(Permission p);
	Permission updatePermission(Permission p);
	Permission removePermission(Permission p);
	Permission findPermissionById(Long id);
	Permission findPermissionByName(String name);
	List<Permission> findAll();
}
