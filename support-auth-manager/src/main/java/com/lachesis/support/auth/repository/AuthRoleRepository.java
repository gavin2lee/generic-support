package com.lachesis.support.auth.repository;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lachesis.support.auth.model.AuthRole;

public interface AuthRoleRepository {
	AuthRole findById(@Param("id") Serializable id);
	List<AuthRole> findByIds(@Param("ids") List<Long> ids);
	List<AuthRole> findByUserId(@Param("userId") Serializable userId);
}
