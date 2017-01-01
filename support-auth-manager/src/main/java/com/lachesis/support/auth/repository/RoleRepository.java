package com.lachesis.support.auth.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.lachesis.support.objects.entity.auth.Permission;
import com.lachesis.support.objects.entity.auth.Role;
import com.lachesis.support.objects.entity.auth.RolePermission;

public interface RoleRepository {
	Role findOne(@Param("id") Long id);

	Role findOneByName(@Param("name") String name);

	List<Role> findAll();

	List<Role> findByUserId(@Param("userId") Long userId);

	Long insertOne(Role r);

	Integer updateOne(Role r);

	@Delete("delete from T_ROLE where id=#{id}")
	Integer deleteOne(@Param("id") Long id);
	
	Long addPermission(RolePermission rolePerm);
	
	Integer addPermissions(List<RolePermission> rolePerms);

	Integer deletePermissions(@Param("roleId") Long roleId,@Param("perms") List<Permission> perms);

	Integer deletePermission(@Param("roleId") Long roleId, @Param("perm") Permission perm);
}
