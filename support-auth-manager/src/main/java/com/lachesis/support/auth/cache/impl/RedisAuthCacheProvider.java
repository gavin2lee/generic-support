package com.lachesis.support.auth.cache.impl;

import javax.annotation.PostConstruct;

import com.lachesis.support.auth.cache.AuthCache;
import com.lachesis.support.auth.cache.AuthCacheProvider;

public class RedisAuthCacheProvider implements AuthCacheProvider {
	
	private RedisAuthCache authTokenCache;
	
	@PostConstruct
	public void postConstruct(){
		
	}

	@Override
	public AuthCache getAuthTokenCache() {
		return authTokenCache;
	}

	@Override
	public AuthCache getAuthorizationResultCache() {
		throw new UnsupportedOperationException();
	}

}
