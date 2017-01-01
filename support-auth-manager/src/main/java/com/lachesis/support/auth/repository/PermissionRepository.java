package com.lachesis.support.auth.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.lachesis.support.objects.entity.auth.Permission;

public interface PermissionRepository {
	Permission findOne(@Param("id") Long id);
	Permission findOneByName(@Param("name") String name);
	
	List<Permission> findAll();
	List<Permission> findByRoleId(@Param("roleId") Long roleId);
	
	Long insertOne(Permission p);
	Integer updateOne(Permission p);
	
	@Delete("delete from T_PERMISSION where id=#{id}")
	Integer deleteOne(@Param("id") Long id);
}
