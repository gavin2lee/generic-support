package com.lachesis.support.auth.repository;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;

import com.lachesis.support.auth.model.AuthUser;

public interface AuthUserRepository {
	AuthUser findById(@Param("id") Serializable id);
	AuthUser findByUsername(@Param("username")String username);
}
