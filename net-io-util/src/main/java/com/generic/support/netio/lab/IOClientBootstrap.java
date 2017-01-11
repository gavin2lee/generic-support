package com.generic.support.netio.lab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.generic.support.netio.lab.bio.BioTimeStampEchoClient;

public class IOClientBootstrap {
	private static final Logger log = LoggerFactory.getLogger(IOClientBootstrap.class);
	private String defaultHost = "localhost";
	private String defaultPort = "9009";
	private String defaultThreads = "1";
	private String defaultTimes = "1";

	private String host;
	private int port;
	private int threads;
	private int times = 100;

	public void boot() {
		setInitParams();
		log.debug(String.format("%s [host:%s,port:%d,times:%d]", IOClientBootstrap.class.getSimpleName(), host, port,
				times));
		for (int i = 0; i < threads; i++) {
			IOClient client = new BioTimeStampEchoClient(host, port, times);
			new Thread(client).start();
		}
	}

	private void setInitParams() {
		String host = System.getProperty("host", defaultHost);
		int port = Integer.parseInt(System.getProperty("port", defaultPort));
		int threads = Integer.parseInt(System.getProperty("threads", defaultThreads));
		int times = Integer.parseInt(System.getProperty("times", defaultTimes));

		this.host = host;
		this.port = port;
		this.threads = threads;
		this.times = times;
	}

	public static void main(String[] args) {
		new IOClientBootstrap().boot();
	}

}
