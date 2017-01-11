package com.generic.support.netio.client;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.generic.support.netio.client.bio.BioTimeStampEchoClient;

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

	private String defaultClientClassName = BioTimeStampEchoClient.class.getName();
	private Class<?> clientClass;

	public void boot() {
		setInitParams();
		log.debug(String.format("%s [host:%s,port:%d,times:%d]", clientClass.getSimpleName(), host, port,
				times));
		Constructor<?> constructor;
		try {
			constructor = clientClass.getConstructor(String.class, int.class, int.class);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
		for (int i = 0; i < threads; i++) {
//			IOClient client = new BioTimeStampEchoClient(host, port, times);
			IOClient client;
			try {
				client = (IOClient) constructor.newInstance(host,port,times);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException cause){
				throw new RuntimeException(cause);
			}
			new Thread(client).start();
		}
	}

	private void setInitParams() {
		String host = System.getProperty("host", defaultHost);
		int port = Integer.parseInt(System.getProperty("port", defaultPort));
		int threads = Integer.parseInt(System.getProperty("threads", defaultThreads));
		int times = Integer.parseInt(System.getProperty("times", defaultTimes));
		String clientClassName = System.getProperty("client", defaultClientClassName);

		try {
			clientClass = Class.forName(clientClassName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		this.host = host;
		this.port = port;
		this.threads = threads;
		this.times = times;
	}

	public static void main(String[] args) {
		new IOClientBootstrap().boot();
	}

}
