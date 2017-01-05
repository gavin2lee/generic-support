package com.lachesis.support.auth.cache.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lachesis.support.auth.cache.AuthCache;

import net.sf.ehcache.Cache;

@Service("ehCacheRedisAuthCacheProvider")
public class CompositeEhCacheRedisAuthCacheProvider extends RedisAuthCacheProvider {
	
	@Autowired
	@Qualifier("authorizationResultCache")
	private Cache authorizationResultEhcache;
	
	private EhcacheBasedAuthCache authorizationResultCache;

	@PostConstruct
	public void postConstruct(){
		super.postConstruct();
		init();
	}
	
	@Override
	public AuthCache getAuthorizationResultCache() {
		return authorizationResultCache;
	}
	
	private void init(){

		if(authorizationResultEhcache == null){
			throw new RuntimeException();
		}
		
		authorizationResultCache = new EhcacheBasedAuthCache(authorizationResultEhcache);
	}
	
}
