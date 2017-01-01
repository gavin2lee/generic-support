package com.lachesis.support.auth.authentication;

import com.lachesis.support.auth.vo.UserDetails;
import com.lachesis.support.auth.vo.UsernamePasswordToken;

public interface Authenticator {
	UserDetails authenticate(UsernamePasswordToken credential);
}
