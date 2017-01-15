package com.generic.support.netio.bootstrap;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.generic.support.netio.client.IOClient;
import com.generic.support.netio.client.bio.BioTimeStampEchoClient;

public class IOClientBootstrap {
	private static final Logger log = LoggerFactory.getLogger(IOClientBootstrap.class);
	private String defaultHost = "localhost";
	private String defaultPort = "9008";
	private String defaultThreads = "1";
	private String defaultTimes = "1";

	private String host;
	private int port;
	private int threads;
	private int times = 100;

	private String defaultClientClassName = BioTimeStampEchoClient.class.getName();
	private Class<?> clientClass;

	//com.generic.support.netio.client.bio.BioTimeStampEchoClient
	//com.generic.support.netio.client.netty.NettyTimeStampEchoClient
	//com.generic.support.netio.client.netty.NettyLoginClient
	//com.generic.support.netio.client.netty.NettyLoginOnceClient
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
		List<Thread> childThreads = new LinkedList<Thread>();
		for (int i = 0; i < threads; i++) {
			IOClient client;
			try {
				client = (IOClient) constructor.newInstance(host,port,times);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException cause){
				throw new RuntimeException(cause);
			}
			Thread t = new Thread(client);
			childThreads.add(t);
			t.start();
		}
		
		for(Thread childThread : childThreads){
			try {
				childThread.join();
			} catch (InterruptedException e) {
				log.warn(e.getMessage(), e);
			}
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
		
		log.info("INIT:" + toString());
	}

	public static void main(String[] args) {
		Date stTime = new Date();
		final long st = System.currentTimeMillis();
		Runtime.getRuntime().addShutdownHook(new Thread(){

			@Override
			public void run() {
				long end = System.currentTimeMillis();
				double seconds = (end - st)/1000.0;
				log.warn(String.format("%s shutdown and elapse %s seconds", IOClientBootstrap.class.getSimpleName(), seconds));
			}
			
		});
		new IOClientBootstrap().boot();
		Date endTime = new Date();
		log.debug("bootstrap start at " + stTime.toString());
		log.debug("bootstrap end at " + endTime.toString());
	}

	@Override
	public String toString() {
		return "IOClientBootstrap [host=" + host + ", port=" + port + ", threads=" + threads + ", times=" + times
				+ ", clientClass=" + clientClass + "]";
	}

}
