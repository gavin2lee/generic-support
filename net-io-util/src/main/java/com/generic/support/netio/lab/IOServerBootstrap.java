package com.generic.support.netio.lab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.generic.support.netio.lab.bio.BioTimeStampEchoServer;

public class IOServerBootstrap {
	public static final int PORT = 9009;
	private static final Logger log = LoggerFactory.getLogger(IOServerBootstrap.class);
	private static final IOServer SERVER = new BioTimeStampEchoServer();

	public static void main(String[] args) {
		log.info(String.format("start %s %d", SERVER.getClass().getSimpleName(), PORT));
		try {
			SERVER.start(PORT);
			log.info(String.format("%s listen %d", SERVER.getClass().getSimpleName(), PORT));
		} catch (Exception e) {
			log.error("", e);
		}

	}

}
