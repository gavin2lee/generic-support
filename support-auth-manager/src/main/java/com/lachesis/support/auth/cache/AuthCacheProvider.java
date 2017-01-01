package com.lachesis.support.auth.cache;

public interface AuthCacheProvider {
	AuthCache getAuthTokenCache();
	AuthCache getAuthorizationResultCache();
}
