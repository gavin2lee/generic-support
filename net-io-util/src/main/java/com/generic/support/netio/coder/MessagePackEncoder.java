package com.generic.support.netio.coder;

import org.msgpack.MessagePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessagePackEncoder extends MessageToByteEncoder<Object> {
	private static final Logger log = LoggerFactory.getLogger(MessagePackEncoder.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		log.debug("encoder:" + msg.getClass().getName());
		MessagePack msgpack = new MessagePack();
		
		byte[] raw = msgpack.write(msg);
		out.writeBytes(raw);
	}

}
