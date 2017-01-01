package com.lachesis.support.auth.cache.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lachesis.support.auth.cache.AuthCache;
import com.lachesis.support.auth.cache.AuthCacheProvider;

import net.sf.ehcache.Cache;

@Service("defaultAuthCacheProvider")
public class DefaultAuthCacheProvider implements AuthCacheProvider {
	
	@Autowired
	@Qualifier("authTokenCache")
	private Cache authTokenEhcache;
	
	@Autowired
	@Qualifier("authorizationResultCache")
	private Cache authorizationResultEhcache;
	
	private EhcacheBasedAuthCache authTokenCache;
	
	private EhcacheBasedAuthCache authorizationResultCache;
	
	@PostConstruct
	public void PostConstruct(){
		this.init();
	}

	@Override
	public AuthCache getAuthTokenCache() {
		return authTokenCache;
	}

	@Override
	public AuthCache getAuthorizationResultCache() {
		return authorizationResultCache;
	}
	
	private void init(){
		if(authTokenEhcache == null){
			throw new RuntimeException();
		}
		
		if(authorizationResultEhcache == null){
			throw new RuntimeException();
		}
		
		authTokenCache = new EhcacheBasedAuthCache(authTokenEhcache);
		authorizationResultCache = new EhcacheBasedAuthCache(authorizationResultEhcache);
	}

}
