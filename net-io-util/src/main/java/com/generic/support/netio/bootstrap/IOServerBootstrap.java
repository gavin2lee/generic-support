package com.generic.support.netio.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.generic.support.netio.server.IOServer;
import com.generic.support.netio.server.netty.NettyTimeStampEchoServer;

public class IOServerBootstrap {
	private static final Logger log = LoggerFactory.getLogger(IOServerBootstrap.class);
	public static final int DEFAULT_PORT = 9009;
	private static IOServer server = null;
	private static String DEFAULT_SERVER_CLASSNAME = NettyTimeStampEchoServer.class.getName();

	private int port;
	
	public void boot(String[] args){
		initServer(args);
		log.info(String.format("start %s %d", server.getClass().getSimpleName(), port));
		try {
			server.start(port);
			log.info(String.format("%s listen %d", server.getClass().getSimpleName(), port));
		} catch (Exception e) {
			log.error("", e);
		}
	}
	
	private void initServer(String[] args) {
		String serverClassName = DEFAULT_SERVER_CLASSNAME;
		if (args.length > 0) {
			serverClassName = args[0];
		}
		try {
			Class<?> serverClass = Class.forName(serverClassName);
			server = (IOServer) serverClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		String sPort = System.getProperty("port");
		if (sPort != null && sPort.trim().length() > 0) {
			port = Integer.parseInt(sPort);
		}else{
			port = DEFAULT_PORT;
		}
	}
	

	// com.generic.support.netio.server.netty.NettyTimeStampEchoServer
	// com.generic.support.netio.server.bio.BioTimeStampEchoServer

	public static void main(String[] args) {
		new IOServerBootstrap().boot(args);
	}

	

}
