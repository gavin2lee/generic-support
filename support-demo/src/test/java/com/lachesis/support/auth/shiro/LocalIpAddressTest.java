package com.lachesis.support.auth.shiro;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalIpAddressTest {
	private static final Logger LOG = LoggerFactory.getLogger(LocalIpAddressTest.class);
	
	@Test
	public void testLocalIpAddress(){
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
			String localIpAddress = addr.getHostAddress();
			LOG.debug("LOCAL IP ADDR:"+localIpAddress);
		} catch (UnknownHostException e) {
			LOG.error("errors", e);
		}
	}
}
