package com.lachesis.support.auth.verifier.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lachesis.support.auth.verifier.TokenVerifyingStrategy;
import com.lachesis.support.objects.entity.auth.Token;

public abstract class AbstractTokenVerifyingStrategy implements TokenVerifyingStrategy {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractTokenVerifyingStrategy.class);
	@Override
	public Token verify(String token, String terminalIpAddress) {
		if(LOG.isDebugEnabled()){
			LOG.debug(String.format("verifying [token:%s,ip:%s]", token, terminalIpAddress));
		}
		
		if(StringUtils.isBlank(token) || StringUtils.isBlank(terminalIpAddress)){
			LOG.error(String.format("[token:%s,ip:%s] is invalid.", token, terminalIpAddress));
			return null;
		}
		return doVerify(token, terminalIpAddress);
	}
	
	protected abstract Token doVerify(String token, String terminalIpAddress);
}
