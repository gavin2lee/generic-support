package com.lachesis.support.auth.cache;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

public class TokenCacheEventListener implements CacheEventListener {
	private CacheEventProcessor cacheEventProcessor;
	
	public TokenCacheEventListener(CacheEventProcessor cacheEventProcessor){
		this.cacheEventProcessor = cacheEventProcessor;
	}
	
	protected CacheEvent createCacheEvent(CacheEventType type,Ehcache cache, Element element){
		String cacheName = cache.getName();
		Object objectValue = element.getObjectValue();
		return new CacheEvent(type,cacheName,objectValue,element,cache);
	}

	@Override
	public void notifyElementRemoved(Ehcache cache, Element element) throws CacheException {
		this.cacheEventProcessor.process(createCacheEvent(CacheEventType.REMOVE, cache, element));
	}

	@Override
	public void notifyElementPut(Ehcache cache, Element element) throws CacheException {
		this.cacheEventProcessor.process(createCacheEvent(CacheEventType.PUT, cache, element));
	}

	@Override
	public void notifyElementUpdated(Ehcache cache, Element element) throws CacheException {
		this.cacheEventProcessor.process(createCacheEvent(CacheEventType.UPDATE, cache, element));
	}

	@Override
	public void notifyElementExpired(Ehcache cache, Element element) {
		this.cacheEventProcessor.process(createCacheEvent(CacheEventType.EXPIRE, cache, element));
	}

	@Override
	public void notifyElementEvicted(Ehcache cache, Element element) {
		this.cacheEventProcessor.process(createCacheEvent(CacheEventType.EVICT, cache, element));
	}

	@Override
	public void notifyRemoveAll(Ehcache cache) {
		this.cacheEventProcessor.process(createCacheEvent(CacheEventType.REMOVEALL, cache, null));
	}

	@Override
	public void dispose() {

	}

	public Object clone() throws CloneNotSupportedException {
		return this.clone();
	}

}
