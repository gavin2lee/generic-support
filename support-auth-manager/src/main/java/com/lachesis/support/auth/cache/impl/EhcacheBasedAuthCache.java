package com.lachesis.support.auth.cache.impl;

import org.apache.commons.lang3.StringUtils;

import com.lachesis.support.auth.cache.AuthCache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

public class EhcacheBasedAuthCache implements AuthCache {
	private Cache ehcache;
	
	public EhcacheBasedAuthCache(Cache ehcache){
		if(ehcache == null){
			throw new RuntimeException();
		}
		this.ehcache = ehcache;
	}

	@Override
	public void put(String key, Object value) {
		if(isBlank(key) || (value == null)){
			throw new RuntimeException();
		}
		ehcache.put(new Element(key,value));
	}

	@Override
	public Object update(String key, Object value) {
		if(isBlank(key) || (value == null)){
			throw new RuntimeException();
		}
		try{
			ehcache.acquireWriteLockOnKey(key);
			ehcache.replace(new Element(key,value));
		}finally{
			ehcache.releaseWriteLockOnKey(key);
		}
		
		return value;

	}

	@Override
	public Object get(String key) {
		if(isBlank(key)){
			throw new RuntimeException();
		}
		
		Element element = ehcache.get(key);
		if(element == null){
			return null;
		}
		return element.getObjectValue();
	}

	@Override
	public Object remove(String key) {
		if(isBlank(key)){
			throw new RuntimeException();
		}

		Element element = ehcache.get(key);
		
		if(element == null){
			return null;
		}
		
		Object objToReturn = element.getObjectValue();
		try{
			ehcache.acquireWriteLockOnKey(key);
			ehcache.remove(key);
		}finally{
			ehcache.releaseWriteLockOnKey(key);
		}
		
		return objToReturn;
	}
	
	private boolean isBlank(String s){
		return StringUtils.isBlank(s);
	}

}
