package com.lachesis.support.auth.cache.impl;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lachesis.support.auth.cache.CacheEvent;
import com.lachesis.support.auth.cache.CacheEventProcessor;
import com.lachesis.support.auth.cache.CacheEventType;
import com.lachesis.support.auth.cache.TokenQueueBroker;
import com.lachesis.support.objects.entity.auth.Token;

@Component("cacheEventProcessor")
public class CompositeCacheEventProcessor implements CacheEventProcessor {
	private static final Logger LOG = LoggerFactory.getLogger(CompositeCacheEventProcessor.class);

	private static final int ACCEPT_PUT = 0;
	private static final int DEFAULT_ACCEPTABLE_PERIOD_SECONDS = 30;

	@Autowired
	private TokenQueueBroker tokenQueueBroker;
	private boolean putAllTokens = false;
	private int acceptablePeriodSeconds = DEFAULT_ACCEPTABLE_PERIOD_SECONDS;

	private DateTimeComparator dateTimeComparator = DateTimeComparator.getInstance();

	public void setTokenQueueBroker(TokenQueueBroker tokenQueueBroker) {
		this.tokenQueueBroker = tokenQueueBroker;
	}

	public void setPutAllTokens(boolean putAllTokens) {
		this.putAllTokens = putAllTokens;
	}

	public void setAcceptablePeriodSeconds(int acceptablePeriodSeconds) {
		this.acceptablePeriodSeconds = acceptablePeriodSeconds;
	}

	@Override
	public void process(CacheEvent event) {
		if(event == null){
			LOG.error("event cannot be null");
			return;
		}
		
		if(LOG.isTraceEnabled()){
			LOG.trace(String.format("process %s", event));
		}
		dispatch(event);
	}

	protected void dispatch(CacheEvent event) {
		CacheEventType type = event.getEventType();
		switch (type) {
		case PUT:
			processPut(event);
			break;
		case UPDATE:
			processUpdate(event);
			break;
		case REMOVE:
			processRemove(event);
			break;
		case EVICT:
			processEvict(event);
			break;
		case EXPIRE:
			processExpire(event);
			break;
		default:
			LOG.warn("such event type not supported currently:" + type);
		}
	}

	protected Token extractTokenFromCacheEvent(CacheEvent event) {
		Object objectValue = event.getObjectValue();
		if (objectValue instanceof Token) {
			return (Token) objectValue;
		}

		return null;
	}

	protected boolean acceptToPut(Token t) {
		DateTime lastModified = new DateTime(t.getLastModified());
		DateTime acceptTime = DateTime.now().minusSeconds(acceptablePeriodSeconds);

		int ret = dateTimeComparator.compare(lastModified, acceptTime);
		if (ret < ACCEPT_PUT) {
			return false;
		}
		return true;
	}

	protected void processPut(CacheEvent event) {
		Token t = extractTokenFromCacheEvent(event);
		if (!putAllTokens && (!acceptToPut(t))) {
			return;
		}

		if (t != null) {
			tokenQueueBroker.addPutToken(t);
		}
	}

	protected void processUpdate(CacheEvent event) {
		Token t = extractTokenFromCacheEvent(event);
		if (t != null) {
			tokenQueueBroker.addUpdateToken(t);
		}
	}

	protected void processRemove(CacheEvent event) {
		Token t = extractTokenFromCacheEvent(event);
		if (t != null) {
			tokenQueueBroker.addRemoveToken(t);
		}
	}

	protected void processEvict(CacheEvent event) {
		Token t = extractTokenFromCacheEvent(event);
		if (t != null) {
			tokenQueueBroker.addEvictToken(t);
		}
	}

	protected void processExpire(CacheEvent event) {
		Token t = extractTokenFromCacheEvent(event);
		if (t != null) {
			tokenQueueBroker.addExpireToken(t);
		}
	}

}
