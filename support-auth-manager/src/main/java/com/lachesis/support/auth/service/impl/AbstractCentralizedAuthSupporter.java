package com.lachesis.support.auth.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lachesis.support.auth.service.CentralizedAuthSupporter;
import com.lachesis.support.auth.vo.AuthenticationResult;
import com.lachesis.support.auth.vo.AuthorizationResult;

public abstract class AbstractCentralizedAuthSupporter implements CentralizedAuthSupporter {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractCentralizedAuthSupporter.class);

	public AuthenticationResult authenticate(String userid, String password, String terminalIpAddress) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("generating token for [userid:%s,ip:%s]", userid, terminalIpAddress));
		}

		String passwordMask = (isBlank(password) ? null : "***");

		if (isBlank(userid) || isBlank(password) || isBlank(terminalIpAddress)) {
			LOG.error(String.format("authenticate failed for [userid:%s,password:%s,ip:%s]", userid, passwordMask,
					terminalIpAddress));
			throw new RuntimeException("generating token failed");
		}

		return doAuthenticate(userid, password, terminalIpAddress);
	}

	public AuthorizationResult authorize(String token, String terminalIpAddress) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("authenticating token for [token:%s,ip:%s]", token, terminalIpAddress));
		}

		if (isBlank(token) || isBlank(terminalIpAddress)) {
			LOG.warn(String.format("authenticating failed for [token:%s,ip:%s]", token, terminalIpAddress));
			return null;
		}

		return doAuthorize(token, terminalIpAddress);
	}

	@Override
	public void logout(String token) {
		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("dismissing token:%s", token));
		}

		if (isBlank(token)) {
			return;
		}

		doLogout(token);

	}

	protected boolean isBlank(String s) {
		return StringUtils.isBlank(s);
	}

	protected abstract AuthenticationResult doAuthenticate(String userid, String password, String terminalIpAddress);

	protected abstract AuthorizationResult doAuthorize(String token, String terminalIpAddress);

	protected abstract void doLogout(String token);
}
