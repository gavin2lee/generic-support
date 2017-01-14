package com.generic.support.netio.coder;

import java.util.List;

import org.msgpack.MessagePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.generic.support.netio.dto.LoginResponse;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class MessagePackClientDecoder extends MessageToMessageDecoder<ByteBuf> {
	private static final Logger log = LoggerFactory.getLogger(MessagePackClientDecoder.class);

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		int len = msg.readableBytes();
		byte[] recvBuf = new byte[msg.readableBytes()];
		msg.getBytes(msg.readerIndex(), recvBuf, 0, len);

		MessagePack msgpack = new MessagePack();
		Object obj = msgpack.read(recvBuf, LoginResponse.class);
		log.debug("decoder:" + obj.getClass().getName());
		out.add(obj);
	}

}
