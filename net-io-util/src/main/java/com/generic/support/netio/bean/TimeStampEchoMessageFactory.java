package com.generic.support.netio.bean;

import java.util.concurrent.atomic.AtomicLong;

public class TimeStampEchoMessageFactory implements EchoMessageFactory {
	private AtomicLong count = new AtomicLong();
	
	private static TimeStampEchoMessageFactory instance = new TimeStampEchoMessageFactory();
	
	private TimeStampEchoMessageFactory(){}
	
	public static TimeStampEchoMessageFactory getInstance(){
		return instance;
	}

	@Override
	public EchoMessage create(String recvMsg) {
		EchoMessage msg = new TimeStampEchoMessage(recvMsg, count.incrementAndGet());
		
		return msg;
	}

}
