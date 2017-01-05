package com.lachesis.support.auth.token.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lachesis.support.auth.cache.AuthCacheProvider;
import com.lachesis.support.auth.data.TokenService;
import com.lachesis.support.auth.token.TokenStorageStrategy;
import com.lachesis.support.objects.entity.auth.Token;

@Service("cacheBasedTokenStorageStrategy")
public class CacheBasedTokenStorageStrategy implements TokenStorageStrategy {
	private static final Logger LOG = LoggerFactory.getLogger(CacheBasedTokenStorageStrategy.class);
	
	@Autowired
	private AuthCacheProvider authCacheProvider;
	
	@Autowired
	@Qualifier("redisTokenService")
	private TokenService tokenService;
	
	private boolean loadFromBackStorage = true;

	public void setLoadFromBackStorage(boolean loadFromDatabase) {
		this.loadFromBackStorage = loadFromDatabase;
	}
	
	public void setTokenService(TokenService tokenService) {
		this.tokenService = tokenService;
	}
	
	@Override
	public void save(Token authToken) {
		if(authToken == null){
			LOG.error("auth token to save should be specified");
			throw new IllegalArgumentException();
		}
		
		if(StringUtils.isBlank(authToken.getTokenValue())){
			LOG.error("token value should be provided");
			throw new IllegalArgumentException();
		}
		
		authCacheProvider.getAuthTokenCache().put(authToken.getTokenValue(), authToken);

	}

	@Override
	public void update(Token authToken) {
		if(authToken == null){
			LOG.error("auth token to save should be specified");
			throw new IllegalArgumentException();
		}
		
		if(StringUtils.isBlank(authToken.getTokenValue())){
			LOG.error("token value should be provided");
			throw new IllegalArgumentException();
		}

		authCacheProvider.getAuthTokenCache().update(authToken.getTokenValue(), authToken);
	}

	@Override
	public Token find(String tokenValue) {
		if(StringUtils.isBlank(tokenValue)){
			LOG.error("token value should be provided");
			throw new IllegalArgumentException();
		}
		Token t = (Token) authCacheProvider.getAuthTokenCache().get(tokenValue);
		if(t == null && loadFromBackStorage ){
			t = tokenService.findByTokenValue(tokenValue);
			save(t);
		}
		
		return t;
	}

	@Override
	public Token remove(String tokenValue) {
		if(StringUtils.isBlank(tokenValue)){
			LOG.error("token value should be provided");
			throw new IllegalArgumentException();
		}
		
		Token authTokenToRemove = (Token) authCacheProvider.getAuthTokenCache().get(tokenValue);
		if(authTokenToRemove == null){
			LOG.warn("no such token found for "+tokenValue);
			return null;
		}
		
		Token authTokenToReturn = (Token) authCacheProvider.getAuthTokenCache().remove(tokenValue);
		return authTokenToReturn;
	}

}
