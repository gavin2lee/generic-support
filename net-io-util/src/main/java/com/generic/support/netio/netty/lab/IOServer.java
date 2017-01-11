package com.generic.support.netio.netty.lab;

public interface IOServer {
	String CLOSE_SIG = "shutdown";
	void start(int port) throws Exception;
}
