package com.generic.support.netio.lab;

public interface IOServer {
	String CLOSE_SIG = "shutdown";
	void start(int port) throws Exception;
}
