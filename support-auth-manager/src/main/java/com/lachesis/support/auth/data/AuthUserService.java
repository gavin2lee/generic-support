package com.lachesis.support.auth.data;

import com.lachesis.support.auth.model.AuthUser;

public interface AuthUserService {
	AuthUser findAuthUserByUsername(String username);
	AuthUser findAuthorizationInfoByUsername(String userid);
}
