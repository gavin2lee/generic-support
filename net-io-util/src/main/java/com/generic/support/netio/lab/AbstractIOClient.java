package com.generic.support.netio.lab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractIOClient implements IOClient {
	private static final Logger log = LoggerFactory.getLogger(AbstractIOClient.class);

	@Override
	public void run() {
		try {
			kickoff();
		} catch (Exception e) {
			log.error("", e);
		}

	}

}
