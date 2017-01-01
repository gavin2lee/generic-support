package com.lachesis.common;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonDirectoryTest {
	static final Logger LOG = LoggerFactory.getLogger(CommonDirectoryTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testUserHome() {
		LOG.info("user.home:"+System.getProperty("user.home"));
		LOG.info("java.home:"+System.getProperty("java.home"));
	}

}
