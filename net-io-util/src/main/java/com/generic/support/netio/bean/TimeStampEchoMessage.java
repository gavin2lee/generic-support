package com.generic.support.netio.bean;

import java.util.Date;

public class TimeStampEchoMessage extends EchoMessage {
	private Date timeStamp = new Date();
	private String threadName = Thread.currentThread().getName();
	private String content;
	private long count;
	public TimeStampEchoMessage(String content, long count) {
		super();
		this.content = content;
		this.count = count;
	}
	@Override
	public String toString() {
		return "TimeStampEchoMessage [timeStamp=" + timeStamp + ", threadName=" + threadName + ", content=" + content
				+ ", count=" + count + "]";
	}
}
