package com.lachesis.support.auth.context.comm.impl;

import org.apache.shiro.authc.AuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.generic.support.service.AuthorizationService;
import com.generic.support.vo.AuthorizationResult;
import com.lachesis.support.auth.context.comm.AuthorizationException;
import com.lachesis.support.auth.context.comm.AuthorizationInfoProvider;
import com.lachesis.support.auth.context.vo.AuthorizationInfoVO;
import com.lachesis.support.auth.context.vo.UserDetailVO;

@Component("dubboAuthorizationInfoProvider")
public class DubboAuthorizationInfoProvider implements AuthorizationInfoProvider {

	@Autowired
	private AuthorizationService authzService;

	@Override
	public AuthorizationInfoVO provide(AuthenticationToken token) throws AuthorizationException {
		AuthorizationResult resp = internalAuthorize((String)token.getPrincipal(), (String)token.getCredentials());
		if( resp == null ){
			throw new AuthorizationException("Cannot normally gained authorization infomation.");
		}
		UserDetailVO user = new UserDetailVO(resp.getId(), resp.getUsername());
		
		return new AuthorizationInfoVO(user, resp.getRoles(), resp.getPermissions());
	}

	protected AuthorizationResult internalAuthorize(String token, String terminalIpAddress)
			throws AuthorizationException {

		AuthorizationResult resp = authzService.authorize(token, terminalIpAddress);

		if ( resp == null ) {
			throw new AuthorizationException();
		}
		return resp;
	}
}
