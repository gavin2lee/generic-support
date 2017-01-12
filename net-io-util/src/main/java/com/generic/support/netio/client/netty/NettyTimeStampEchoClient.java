package com.generic.support.netio.client.netty;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.generic.support.netio.client.bio.BioTimeStampEchoClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyTimeStampEchoClient extends BioTimeStampEchoClient {
	private static final Logger log = LoggerFactory.getLogger(NettyTimeStampEchoClient.class);

	public NettyTimeStampEchoClient(String host, int port) {
		super(host, port);
	}

	public NettyTimeStampEchoClient(String host, int port, int times) {
		super(host, port, times);
	}
	

	@Override
	public void kickoff() throws Exception {
		EventLoopGroup grp = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(grp);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.TCP_NODELAY, true);
			b.handler(new NettyTimeStampEchoClientInitializer());

			ChannelFuture f = b.connect(getHost(), getPort()).sync();

			f.channel().closeFuture().sync();

		} finally {
			grp.shutdownGracefully();
		}

	}

	public class NettyTimeStampEchoClientInitializer extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ch.pipeline().addLast(new NettyTimeStampEchoClientHandler());

		}

	}

	public class NettyTimeStampEchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
		private  AtomicLong receiveCounter = new AtomicLong();
		private  AtomicLong sendCounter = new AtomicLong();
		
		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			sendMsg(ctx);
		}

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
			byte [] buf = new byte[msg.readableBytes()];
			msg.readBytes(buf);
			
			String recvMsg = new String(buf, "UTF-8");
			log.debug(Thread.currentThread().getName() + " - RECV " + receiveCounter.incrementAndGet() + ":" + recvMsg);
			
			

		}
		
		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			while(sendCounter.get() > getTimes()){
				log.warn(String.format("send counter:%d - maxtimes:%d - going to close.", sendCounter.get(), getTimes()));
				ctx.flush();
				ctx.disconnect();
				ctx.close();
				
				return;
			}
			
			sendMsg(ctx);
		}

		protected void sendMsg(ChannelHandlerContext ctx) throws Exception{

			String line = NettyTimeStampEchoClient.this.getSentenceGenerator().generate();
			byte [] sendMsg = line.getBytes("UTF-8");
			ByteBuf sendMsgBuf = Unpooled.buffer(sendMsg.length);
			sendMsgBuf.writeBytes(sendMsg);
			
			log.debug(Thread.currentThread().getName() +  "  SEND " + sendCounter.incrementAndGet() + " >>> " + line);
			
			ctx.writeAndFlush(sendMsgBuf);
		}

	}

}
