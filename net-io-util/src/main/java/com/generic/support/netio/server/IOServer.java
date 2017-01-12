package com.generic.support.netio.server;

public interface IOServer {
	String CLOSE_SIG = "shutdown";
	String ENCODING = "UTF-8";
	int BACKLOG_SIZE = 32;
	void start(int port) throws Exception;
}
