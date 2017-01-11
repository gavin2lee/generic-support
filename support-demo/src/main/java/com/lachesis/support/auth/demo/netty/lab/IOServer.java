package com.lachesis.support.auth.demo.netty.lab;

public interface IOServer {
	String CLOSE_SIG = "shutdown";
	void start(int port) throws Exception;
}
