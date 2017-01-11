package com.generic.support.netio.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOServerBootstrap {
	public static final int PORT = 9009;
	private static final Logger log = LoggerFactory.getLogger(IOServerBootstrap.class);
	private static IOServer SERVER = null;
	

	public static void main(String[] args) {
		initServer(args);
		log.info(String.format("start %s %d", SERVER.getClass().getSimpleName(), PORT));
		try {
			SERVER.start(PORT);
			log.info(String.format("%s listen %d", SERVER.getClass().getSimpleName(), PORT));
		} catch (Exception e) {
			log.error("", e);
		}

	}
	
	private static void initServer(String[] args){
		String serverClassName = "com.generic.support.netio.server.bio.BioTimeStampEchoServer";
		if(args.length > 0){
			serverClassName = args[0];
		}
		try {
			Class<?> serverClass = Class.forName(serverClassName);
			SERVER = (IOServer) serverClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
