package com.generic.support.netio.client.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.generic.support.netio.client.AbstractMultipleIOClient;
import com.generic.support.netio.coder.MessagePackDecoder;
import com.generic.support.netio.coder.MessagePackEncoder;
import com.generic.support.netio.dto.LoginRequest;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyLoginClient extends AbstractMultipleIOClient {
	private static final Logger log = LoggerFactory.getLogger(NettyLoginClient.class);

	public NettyLoginClient(String host, int port, int times) {
		super(host, port, times);
	}

	@Override
	public void kickoff() throws Exception {
		log.debug(String.format("%s  kickoff with host:%s,port:%d", NettyLoginClient.class.getSimpleName(), getHost(),
				getPort()));
		EventLoopGroup clientGroup = new NioEventLoopGroup();

		try {
			Bootstrap client = new Bootstrap();
			client.group(clientGroup);

			client.channel(NioSocketChannel.class);
			client.handler(new NettyLoginClientChannelInitializer());

			ChannelFuture cf = client.connect(getHost(), getPort()).sync();
			cf.channel().closeFuture().sync();
		} finally {
			clientGroup.shutdownGracefully();
		}

	}

	public class NettyLoginClientChannelInitializer extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			
			ch.pipeline().addLast("messagePackDecoder", new MessagePackDecoder());
			ch.pipeline().addLast("messagePackEncoder", new MessagePackEncoder());
			
			ch.pipeline().addLast(new NettyLoginClientHandler());
		}

	}
	
	public class NettyLoginClientHandler extends ChannelInboundHandlerAdapter{

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			log.debug("channelActive");
			sendRequest(ctx);
			ctx.flush();
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			log.debug("channelRead");
			log.debug("recv type:"+msg.getClass().getName());
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			log.debug("channelReadComplete");
			sendRequest(ctx);
			ctx.flush();
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			log.error(cause.getMessage(),cause);
			ctx.close();
		}
		
		protected void sendRequest(ChannelHandlerContext ctx){
			LoginRequest req = new LoginRequest();
			req.setUsername("test");
			req.setPassword("abc123");
			
			ctx.write(req);
		}
		
	}

}
