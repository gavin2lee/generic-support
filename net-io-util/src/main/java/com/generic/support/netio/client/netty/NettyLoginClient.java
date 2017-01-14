package com.generic.support.netio.client.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.generic.support.netio.client.AbstractMultipleIOClient;

public class NettyLoginClient extends AbstractMultipleIOClient {
	private static final Logger log = LoggerFactory.getLogger(NettyLoginClient.class);
	

	public NettyLoginClient(String host, int port, int times) {
		super(host, port, times);
	}

	@Override
	public void kickoff() throws Exception {
		// TODO Auto-generated method stub

	}

}
