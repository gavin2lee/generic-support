package com.lachesis.support.auth.repository;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lachesis.support.auth.model.AuthPermission;

public interface AuthPermissionRepository {
	AuthPermission findById(@Param("id") Serializable id);
	List<AuthPermission> findByIds(@Param("ids") Long ids);
	List<AuthPermission> findByRoleId(@Param("roleId") Long roleId);
	List<AuthPermission> findByRoleIds(@Param("roleIds") List<Long> roleIds);
}
