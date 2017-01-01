package com.lachesis.support.auth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lachesis.support.auth.authentication.AuthenticatorProvider;
import com.lachesis.support.auth.authorization.Authorizer;
import com.lachesis.support.auth.authorization.AuthorizerProvider;
import com.lachesis.support.auth.authentication.Authenticator;

@Service("defaultAuthenticationProvider")
public class LocalDatabaseCompositeAuthProvider implements AuthenticatorProvider, AuthorizerProvider {
	private static final Logger LOG = LoggerFactory.getLogger(LocalDatabaseCompositeAuthProvider.class);

	@Autowired
	private Authenticator authenticator;
	
	@Autowired
	private Authorizer authorizer;
	
	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}
	
	public void setAuthorizer(Authorizer authorizer) {
		this.authorizer = authorizer;
	}

	@Override
	public Authenticator getAuthenticator() {
		if(this.authenticator == null){
			throw new RuntimeException();
		}
		
		if(LOG.isDebugEnabled()){
			LOG.debug(String.format("current authenticator is [%s]", authenticator.getClass().getName()));
		}
		return authenticator;
	}

	@Override
	public Authorizer getAuthorizer() {
		if(this.authorizer == null){
			throw new RuntimeException();
		}
		
		if(LOG.isDebugEnabled()){
			LOG.debug(String.format("current authorizer is [%s]", authorizer.getClass().getName()));
		}
		return authorizer;
	}

}
