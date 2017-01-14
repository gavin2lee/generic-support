package com.generic.support.netio.coder;

import java.util.List;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class MessagePackDecoder extends MessageToMessageDecoder<ByteBuf> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		int len = msg.readableBytes();
		byte[] recvBuf = new byte[msg.readableBytes()];
		msg.getBytes(msg.readerIndex(), recvBuf, 0, len);
		
		MessagePack msgpack = new MessagePack();
		out.add(msgpack.read(recvBuf));
	}

}
