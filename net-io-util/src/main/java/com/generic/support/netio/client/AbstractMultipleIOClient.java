package com.generic.support.netio.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractMultipleIOClient implements IOClient {
	private static final Logger log = LoggerFactory.getLogger(AbstractMultipleIOClient.class);
	private int times;
	private String host;
	private int port;
	
	

	public AbstractMultipleIOClient(String host, int port,int times) {
		super();
		this.times = times;
		this.host = host;
		this.port = port;
	}
	
	protected int getTimes() {
		return times;
	}

	protected void setTimes(int times) {
		this.times = times;
	}

	protected String getHost() {
		return host;
	}

	protected void setHost(String host) {
		this.host = host;
	}

	protected int getPort() {
		return port;
	}

	protected void setPort(int port) {
		this.port = port;
	}



	@Override
	public void run() {
		try {
			kickoff();
		} catch (Exception e) {
			log.error("", e);
		}

	}

}
