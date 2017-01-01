package com.lachesis.support.auth.cache;

public interface AuthCache {
	void put(String key,Object value);
	Object update(String key,Object value);
	Object get(String key);
	Object remove(String key);
}
