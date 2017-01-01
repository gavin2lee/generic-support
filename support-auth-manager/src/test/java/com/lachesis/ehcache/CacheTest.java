package com.lachesis.ehcache;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

//import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.lachesis.support.objects.entity.auth.Token;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.RegisteredEventListeners;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

@Ignore
public class CacheTest {
	private AtomicLong oidSequence = new AtomicLong();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore
	@Test
	public void testCache() {
		String cacheName = "AuthTokenCache";
		int maxElementsInMemory = 10;
		MemoryStoreEvictionPolicy memoryStoreEvictionPolicy = MemoryStoreEvictionPolicy.LRU;
		boolean overflowToDisk = true;
		String diskStorePath = null;
		boolean eternal = false;
		long timeToLiveSeconds = 0;
		long timeToIdleSeconds = 15;
		boolean diskPersistent = false;
		long diskExpiryThreadIntervalSeconds = 3;
		RegisteredEventListeners registeredEventListeners = null;

		int maxSizeToStore = 11;

		CacheManager cacheManager = CacheManager.create();

		Cache authTokenCache = new Cache(cacheName, maxElementsInMemory, memoryStoreEvictionPolicy, overflowToDisk,
				diskStorePath, eternal, timeToLiveSeconds, timeToIdleSeconds, diskPersistent,
				diskExpiryThreadIntervalSeconds, registeredEventListeners);

		cacheManager.addCache(authTokenCache);

		Assert.assertEquals("check cache name.", cacheName, cacheManager.getCache(cacheName).getName());

		for (int i = 0; i < maxSizeToStore; i++) {
			Token authToken = generateAuthToken();
			authTokenCache.put(new Element(authToken.getTokenValue(), authToken));
		}

		// Assert.assertEquals("check size", maxElementsInMemory,
		// authTokenCache.getSize());
		Assert.assertEquals("check size", maxSizeToStore, authTokenCache.getSize());

		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Assert.assertEquals("check size after 6 seconds'sleeping", maxSizeToStore, authTokenCache.getSize());

		List<?> keys = authTokenCache.getKeys();
		System.out.println("SIZE:" + keys.size());

		int count = 0;
		for (Object key : keys) {
			if (count > 5) {
				break;
			}
			
			Element e = authTokenCache.get(key);
			if (e != null) {
				Token t = (Token) (authTokenCache.get(key).getObjectValue());
				System.out.println(key + " - " + t.getTerminalIp());
			}

			count++;
		}
		
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("second sleeping");

		for (Object key : keys) {

			Element e = authTokenCache.get(key);
			if (e != null) {
				Token t = (Token) (authTokenCache.get(key).getObjectValue());
				System.out.println(key + " - " + t.getTerminalIp());
			}

			System.out.println("size2 : " + authTokenCache.getSize());

		}
	}

	private Token generateAuthToken() {
		Token t = new Token();
		long oid = oidSequence.incrementAndGet();
		t.setActive(true);
		t.setLastModified(new Date());
		t.setTerminalIp("10.10.10." + oid);
		t.setPassword("123456");
		t.setTokenValue(UUID.randomUUID().toString());
		return t;
	}

}
