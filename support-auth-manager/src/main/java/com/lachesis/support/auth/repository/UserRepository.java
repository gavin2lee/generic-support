package com.lachesis.support.auth.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.lachesis.support.objects.entity.auth.Role;
import com.lachesis.support.objects.entity.auth.User;
import com.lachesis.support.objects.entity.auth.UserRole;

public interface UserRepository {
	User findOne(@Param("id") Long id);
	
	User findOneByUsername(@Param("username") String username);
	
	Long insertOne(User u);
	
	Integer updateOne(User u);
	
	Integer addRole(UserRole userRole);

	Integer addRoles(List<UserRole> userRoles);

	Integer deleteRoles(@Param("userId") Long userId, @Param("roles") List<Role> roles);

	Integer deleteRole(@Param("userId") Long userId, @Param("role") Role role);
	
	Integer deleteAllRoles(@Param("userId") Long userID);

	@Delete("delete from T_USER where ID = #{id}")
	Integer deleteOne(@Param("id") Long id);
}
