package com.generic.support.netio.client;

import com.generic.support.netio.IOConstants;

public interface IOClient extends Runnable, IOConstants{
	void kickoff() throws Exception;
}
