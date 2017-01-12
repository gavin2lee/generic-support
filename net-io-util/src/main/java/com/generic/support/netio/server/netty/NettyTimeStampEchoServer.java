package com.generic.support.netio.server.netty;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.generic.support.netio.bean.EchoMessage;
import com.generic.support.netio.bean.TimeStampEchoMessageFactory;
import com.generic.support.netio.server.IOServer;

import io.netty.bootstrap.ServerBootstrap;
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
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyTimeStampEchoServer implements IOServer {
	private static final Logger log = LoggerFactory.getLogger(NettyTimeStampEchoServer.class);
	private static final AtomicLong counter = new AtomicLong();

	@Override
	public void start(int port) throws Exception {
		EventLoopGroup bossGrp = new NioEventLoopGroup();
		EventLoopGroup workerGrp = new NioEventLoopGroup();

		try {
			ServerBootstrap sb = new ServerBootstrap();
			sb.group(bossGrp, workerGrp);
			sb.channel(NioServerSocketChannel.class);
			sb.option(ChannelOption.SO_BACKLOG, BACKLOG_SIZE);
			sb.childHandler(new NettyTimeStampEchoServerChildHandler());

			ChannelFuture f = sb.bind(port).sync();
			f.channel().closeFuture().sync();

		} finally {
			bossGrp.shutdownGracefully();
			workerGrp.shutdownGracefully();
		}
	}

	public static class NettyTimeStampEchoServerChildHandler extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ch.pipeline().addLast(new NettyTimeStampEchoServerHandler());

		}

	}

	public static class NettyTimeStampEchoServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
		private TimeStampEchoMessageFactory echoMessageFactory = TimeStampEchoMessageFactory.getInstance();

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
			byte [] buf = new byte[msg.readableBytes()];
			msg.readBytes(buf);
			String recvMsg = new String(buf, "UTF-8");
			log.debug(Thread.currentThread().getName() + " - RECV " + counter.incrementAndGet() + ":" + recvMsg);
			EchoMessage echoMsg = echoMessageFactory.create(recvMsg);
			ByteBuf resp = Unpooled.copiedBuffer(echoMsg.toString().getBytes("UTF-8"));
			ctx.write(resp);
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			ctx.flush();
			super.channelReadComplete(ctx);
		}

	}

}
