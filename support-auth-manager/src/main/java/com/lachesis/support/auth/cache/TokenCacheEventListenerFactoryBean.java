package com.lachesis.support.auth.cache;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.lachesis.support.common.util.spring.ApplicationContextUtils;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

public class TokenCacheEventListenerFactoryBean extends CacheEventListenerFactory
		implements FactoryBean<CacheEventListener>, InitializingBean {
	private static final Logger LOG = LoggerFactory.getLogger(TokenCacheEventListenerFactoryBean.class);
	public static final String DEFAULT_BEAN_NAME_EVENT_PROCESSOR = "cacheEventProcessor";
	
	@Autowired
	private CacheEventProcessor cacheEventProcessor;

	private TokenCacheEventListener listener;

	protected CacheEventProcessor getCacheEventProcessor() {
		return cacheEventProcessor;
	}

	public void setCacheEventProcessor(CacheEventProcessor cacheEventProcessor) {
		this.cacheEventProcessor = cacheEventProcessor;
	}

	@Override
	public CacheEventListener createCacheEventListener(Properties properties) {
		if (listener != null) {
			return listener;
		}

		cacheEventProcessor = ApplicationContextUtils.getBean(DEFAULT_BEAN_NAME_EVENT_PROCESSOR,
				CacheEventProcessor.class);
		if (cacheEventProcessor == null) {
			throw new RuntimeException(String.format("no %s identified.", CacheEventProcessor.class.getName()));
		}

		listener = new TokenCacheEventListener(cacheEventProcessor);
		if (LOG.isDebugEnabled()) {
			LOG.debug("created by ehcache: " + TokenCacheEventListener.class.getName());
		}
		return listener;
	}

	@Override
	public CacheEventListener getObject() throws Exception {
		return listener;
	}

	@Override
	public Class<?> getObjectType() {
		return CacheEventListener.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("created when initializing by spring: " + TokenCacheEventListener.class.getName());
		}

		listener = new TokenCacheEventListener(getCacheEventProcessor());
	}

}
