package com.generic.support.auth.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.generic.support.service.AuthorizationService;
import com.generic.support.vo.AuthorizationResult;
import com.lachesis.support.auth.api.common.AuthBizErrorCodes;
import com.lachesis.support.auth.api.exception.AuthenticationException;
import com.lachesis.support.auth.service.CentralizedAuthSupporter;
import com.lachesis.support.common.util.text.TextUtils;

public class ConcreteAuthorizationService implements AuthorizationService {
	private static final Logger LOG = LoggerFactory.getLogger(ConcreteAuthorizationService.class);
	
	@Autowired
	private CentralizedAuthSupporter authSupporter;

	@Override
	public AuthorizationResult authorize(String token, String ip) {
		if (isBlank(token) || isBlank(ip)) {
			LOG.error(String.format("errors with [token:%s, ip:%s]", token, ip));
			return null;
		}
		return doAuthorize(token, ip);
	}
	
	protected AuthorizationResult doAuthorize(String token, String ip){
		com.lachesis.support.auth.vo.AuthorizationResult authzResult = authSupporter.authorize(token, ip);
		if (authzResult == null) {
			LOG.error(String.format("authentication failed with [token:%s, ip:%s]", token, ip));
			throw new AuthenticationException(AuthBizErrorCodes.AUTH_FAILED_TOKEN, "无效token");
		}
		
		return convertAuthorizationResult(authzResult);
	}
	
	private AuthorizationResult convertAuthorizationResult(com.lachesis.support.auth.vo.AuthorizationResult result) {
		AuthorizationResult resp = new AuthorizationResult();
		resp.setId(result.getId());
		resp.setUsername(result.getUsername());
		resp.setRoles(result.getRoles());
		resp.setPermissions(result.getPermissions());

		return resp;
	}

	private boolean isBlank(String s) {
		return TextUtils.isBlank(s);
	}
}
