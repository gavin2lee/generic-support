package com.lachesis.support.auth.verifier.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lachesis.support.auth.verifier.TokenVerifier;
import com.lachesis.support.auth.verifier.TokenVerifyingStrategy;
import com.lachesis.support.objects.entity.auth.Token;

@Service("defaultTokenVerifier")
public class DefaultTokenVerifier implements TokenVerifier {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultTokenVerifier.class);

	@Autowired
	@Qualifier("maxIdleTimeTokenVerifyingStrategy")
	private TokenVerifyingStrategy tokenVerifyStrategy;

	@Override
	public Token verify(String token, String terminalIpAddress) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("verify for [token:%s,ip:%s]", token, terminalIpAddress));
		}
		if (StringUtils.isBlank(token) || StringUtils.isBlank(terminalIpAddress)) {
			return null;
		}
		return doVerify(token, terminalIpAddress);
	}

	protected Token doVerify(String token, String terminalIpAddress) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("concrete verify strategy is " + this.tokenVerifyStrategy.getClass().getName());
		}

		return tokenVerifyStrategy.verify(token, terminalIpAddress);
	}

}
