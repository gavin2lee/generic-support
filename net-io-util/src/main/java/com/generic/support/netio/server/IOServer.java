package com.generic.support.netio.server;

import com.generic.support.netio.IOConstants;

public interface IOServer extends IOConstants{
	void start(int port) throws Exception;
}
