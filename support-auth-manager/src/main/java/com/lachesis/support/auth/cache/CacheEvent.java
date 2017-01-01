package com.lachesis.support.auth.cache;

import com.lachesis.support.objects.entity.auth.Token;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class CacheEvent {
	private CacheEventType eventType;
	private String cacheName;
	private Object objectValue;
	private Element element;
	private Ehcache ehcache;

	public CacheEvent(CacheEventType eventType, String cacheName, Token token) {
		this(eventType, cacheName, token, null, null);
	}

	public CacheEvent(CacheEventType eventType, String cacheName, Object objectValue, Element element,
			Ehcache ehcache) {
		super();
		this.eventType = eventType;
		this.cacheName = cacheName;
		this.objectValue = objectValue;
		this.element = element;
		this.ehcache = ehcache;
	}

	public CacheEventType getEventType() {
		return eventType;
	}

	public String getCacheName() {
		return cacheName;
	}

	public Object getObjectValue() {
		return objectValue;
	}

	public Element getElement() {
		return element;
	}

	public Ehcache getEhcache() {
		return ehcache;
	}

	@Override
	public String toString() {
		return "CacheEvent [eventType=" + eventType + ", cacheName=" + cacheName + ", objectValue=" + objectValue
				+ ", element=" + element + ", ehcache=" + ehcache + "]";
	}

}
