package com.generic.support.netio.server.netty;

import java.net.SocketAddress;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.generic.support.netio.coder.MessagePackEncoder;
import com.generic.support.netio.coder.MessagePackServerDecoder;
import com.generic.support.netio.dto.LoginRequest;
import com.generic.support.netio.dto.LoginResponse;
import com.generic.support.netio.server.IOServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class NettyLoginServer implements IOServer {
	private static final Logger log = LoggerFactory.getLogger(NettyLoginServer.class);
	private final AtomicLong totalRecvMsgAmount = new AtomicLong();
	private final AtomicLong idGenerator = new AtomicLong();

	@Override
	public void start(int port) throws Exception {
		log.debug(String.format("%s start with port:%d", NettyLoginServer.class.getName(), port));
		EventLoopGroup bossGrp = new NioEventLoopGroup();
		EventLoopGroup workerGrp = new NioEventLoopGroup();

		try {
			ServerBootstrap server = new ServerBootstrap();
			server.group(bossGrp, workerGrp);
			server.channel(NioServerSocketChannel.class);
			server.childHandler(new NettyLoginServerChannelInitializer());

			ChannelFuture cf = server.bind(port).sync();
			cf.channel().closeFuture().sync();

		} finally {
			bossGrp.shutdownGracefully();
			workerGrp.shutdownGracefully();
		}

	}

	public class NettyLoginServerChannelInitializer extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			
			ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2,0,2));

			ch.pipeline().addLast("messagePackDecoder", new MessagePackServerDecoder());
			
			ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
			ch.pipeline().addLast("messagePackEncoder", new MessagePackEncoder());
			
			ch.pipeline().addLast(new NettyLoginServerHandler());
		}

	}

	public class NettyLoginServerHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
			log.debug("channelRegistered");
			super.channelRegistered(ctx);
		}

		@Override
		public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
			log.debug("channelUnregistered");
			super.channelUnregistered(ctx);
		}

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			log.debug("channelActive");
			super.channelActive(ctx);
		}

		@Override
		public void channelInactive(ChannelHandlerContext ctx) throws Exception {
			log.debug("channelInactive");
			super.channelInactive(ctx);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			log.debug("channelRead");
			log.debug("recv type: "+msg.getClass().getName());
			log.debug(String.format("server %s %s RECV: %s", Thread.currentThread().getName(), totalRecvMsgAmount.incrementAndGet(), msg));
			
			
			writeBack(ctx, msg);
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			log.debug("channelReadComplete");
			ctx.flush();
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			log.error("", cause);
			ctx.close();
		}
		
		protected void writeBack(ChannelHandlerContext ctx, Object msg){
			LoginRequest req = (LoginRequest)msg;
			LoginResponse resp = new LoginResponse();
			resp.setUserid(idGenerator.incrementAndGet());
			resp.setUsername(req.getUsername());
			resp.setServerIdentity(Thread.currentThread().getName());
			resp.setRoles(Arrays.asList(new String[]{"user", "admin"}));
			
			SocketAddress remoteAddr = ctx.channel().remoteAddress();
			String remoteIp = remoteAddr.toString();
			
			resp.setRemoteAgent("pc");
			resp.setRemoteIp(remoteIp);
			resp.setClientIdentity(req.getClientIdentity());
			ctx.write(resp);
		}
		
	}

}
