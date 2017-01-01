package com.lachesis.support.auth.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lachesis.support.auth.api.common.AuthBizErrorCodes;
import com.lachesis.support.auth.api.exception.AuthenticationException;
import com.lachesis.support.auth.api.vo.AuthResponse;
import com.lachesis.support.auth.api.vo.AuthorizationResponse;
import com.lachesis.support.auth.service.CentralizedAuthSupporter;
import com.lachesis.support.auth.vo.AuthorizationResult;
import com.lachesis.support.common.util.text.TextUtils;

@RestController
public class AuthzController {
	private static final Logger LOG = LoggerFactory.getLogger(AuthzController.class);
	@Autowired
	private CentralizedAuthSupporter authSupporter;

	@RequestMapping(value = "authorizations/{tokenid}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, method = RequestMethod.GET)
	public AuthResponse authorize(@PathVariable("tokenid") String token, @RequestParam("ip") String ip,
			HttpServletRequest request) {
		logAuthorize(token, ip);
		validateAuthorizeParameters(token, ip);

		return convertAuthorizationResult(internalAuthorize(token, ip));
	}

	@RequestMapping(value = "authorizations", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, method = RequestMethod.GET)
	public AuthResponse authorizeWithUsername(@RequestParam("username") String username, HttpServletRequest request) {
		throw new UnsupportedOperationException();
	}

	

	private AuthorizationResult internalAuthorize(String token, String ip) {
		AuthorizationResult authzResult = authSupporter.authorize(token, ip);
		if (authzResult == null) {
			LOG.error(String.format("authentication failed with [token:%s, ip:%s]", token, ip));
			throw new AuthenticationException(AuthBizErrorCodes.AUTH_FAILED_TOKEN, "无效token");
		}

		return authzResult;
	}

	private void validateAuthorizeParameters(String token, String ip) {
		if (isBlank(token) || isBlank(ip)) {
			LOG.error(String.format("errors with [token:%s, ip:%s]", token, ip));
			throw new AuthenticationException(AuthBizErrorCodes.AUTH_FAILED_ARGS, "token或IP为空");
		}
	}

	private void logAuthorize(String token, String ip) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("authorize for [token:%s,ip:%s]", token, ip));
		}
	}

	private AuthorizationResponse convertAuthorizationResult(AuthorizationResult result) {
		AuthorizationResponse resp = new AuthorizationResponse();
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
