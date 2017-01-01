package com.lachesis.support.auth.cache;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lachesis.support.auth.annotation.SupportTestContext;


@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@SupportTestContext
public class DefaultAuthCacheProviderTest {
	
	@Autowired
	AuthCacheProvider provider;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetAuthTokenCache() {
		Assert.assertNotNull("check auth token cache", provider.getAuthTokenCache());
	}

	@Test
	public void testGetAuthorizationResultCache() {
		Assert.assertNotNull("check user details cache", provider.getAuthorizationResultCache());
	}

}
