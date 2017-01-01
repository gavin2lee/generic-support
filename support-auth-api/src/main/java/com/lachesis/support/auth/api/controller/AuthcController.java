package com.lachesis.support.auth.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lachesis.support.auth.api.common.AuthBizErrorCodes;
import com.lachesis.support.auth.api.exception.AuthenticationException;
import com.lachesis.support.auth.api.vo.AuthResponse;
import com.lachesis.support.auth.api.vo.AuthenticationRequest;
import com.lachesis.support.auth.api.vo.AuthenticationResponse;
import com.lachesis.support.auth.service.CentralizedAuthSupporter;
import com.lachesis.support.auth.vo.AuthenticationResult;
import com.lachesis.support.common.util.text.TextUtils;

@RestController
public class AuthcController {
	private static final Logger LOG = LoggerFactory.getLogger(AuthcController.class);

	@Autowired
	private CentralizedAuthSupporter authSupporter;

	@RequestMapping(value = "tokens", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }, method = RequestMethod.POST)
	public AuthResponse authenticate(@RequestBody AuthenticationRequest tokenRequest, HttpServletRequest request) {
		logAuthenticate(tokenRequest);
		validateAuthenticationRequest(tokenRequest);

		AuthenticationResult result = internalAuthenticate(tokenRequest.getUsername(), tokenRequest.getPassword(),
				determineTerminalIpAddress(request));
		return convertAuthenticationResult(result.getTokenValue(), result.getUserId());
	}
	
	@RequestMapping(value = "tokens/{tokenid}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, method = RequestMethod.DELETE)
	public ResponseEntity<String> logout(@PathVariable("tokenid") String token, HttpServletRequest request) {
		authSupporter.logout(token);
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}

	private void logAuthenticate(AuthenticationRequest request) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("authenticate for [username:%s]", request.getUsername()));
		}
	}

	private void validateAuthenticationRequest(AuthenticationRequest request) {
		if (isBlank(request.getUsername()) || isBlank(request.getPassword())) {
			LOG.error(String.format("errors with [username:%s]", request.getUsername()));
			throw new AuthenticationException(AuthBizErrorCodes.AUTH_FAILED_ARGS, "用户名或密码为空");
		}
	}

	private AuthenticationResult internalAuthenticate(String userid, String psword, String ip) {
		AuthenticationResult result = authSupporter.authenticate(userid, psword, ip);
		if ((result == null) || isBlank(result.getTokenValue())) {
			LOG.error(String.format("authenticating failed for [userid:%s, ip:%s]", userid, ip));
			throw new AuthenticationException(AuthBizErrorCodes.AUTH_FAILED, "认证错误");
		}

		return result;
	}

	private AuthenticationResponse convertAuthenticationResult(String token, String userId) {
		AuthenticationResponse tokenResp = new AuthenticationResponse();
		tokenResp.setToken(token);
		tokenResp.setUserId(userId);

		return tokenResp;
	}

	private String determineTerminalIpAddress(HttpServletRequest request) {
		String ip = request.getRemoteAddr();
		return ip;
	}

	private boolean isBlank(String s) {
		return TextUtils.isBlank(s);
	}
}
