package com.generic.support.netio.server;

public interface IOServer {
	String CLOSE_SIG = "shutdown";
	void start(int port) throws Exception;
}
