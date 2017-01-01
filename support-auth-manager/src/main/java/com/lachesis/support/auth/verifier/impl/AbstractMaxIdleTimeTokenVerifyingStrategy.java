package com.lachesis.support.auth.verifier.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.lachesis.support.auth.common.AuthConstants;
import com.lachesis.support.auth.token.AuthTokenManager;
import com.lachesis.support.objects.entity.auth.Token;

public abstract class AbstractMaxIdleTimeTokenVerifyingStrategy extends AbstractTokenVerifyingStrategy{
	private static final Logger LOG = LoggerFactory.getLogger(AbstractMaxIdleTimeTokenVerifyingStrategy.class);
	
	@Value("${support.auth.verifier.max.idle.time.seconds}")
	private int tokenMaxIdleSeconds = AuthConstants.TOKEN_MAX_IDLE_SECONDS;

	@Autowired
	private AuthTokenManager tokenHolder;

	@Override
	protected Token doVerify(String token, String terminalIpAddress) {
		Token authToken = tokenHolder.retrieve(token);

		if (authToken == null) {
			LOG.debug(String.format("cannot retrieve [token:%s]", token));
			return null;
		}

		if (isExpired(authToken)) {
			LOG.debug(String.format("[token:%s] expired and remove from cache", token));
			tokenHolder.dismiss(authToken.getTokenValue());
			return null;
		}

		tokenHolder.updateLastModifiedTime(authToken);
		return authToken;
	}
	
	protected boolean isExpired(Token token){
		long lastModifiedTime = token.getLastModified().getTime();
		long currentTime = System.currentTimeMillis();
		
		long idleTime = (currentTime - lastModifiedTime);
		return (idleTime > (getTokenMaxIdleSeconds() * 1000) );
	}

	protected int getTokenMaxIdleSeconds() {
		return this.tokenMaxIdleSeconds;
	}
}
