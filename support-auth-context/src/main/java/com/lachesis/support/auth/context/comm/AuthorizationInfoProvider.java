package com.lachesis.support.auth.context.comm;

import org.apache.shiro.authc.AuthenticationToken;

import com.lachesis.support.auth.context.vo.AuthorizationInfoVO;

public interface AuthorizationInfoProvider {
	AuthorizationInfoVO provide(AuthenticationToken token) throws AuthorizationException;
}
