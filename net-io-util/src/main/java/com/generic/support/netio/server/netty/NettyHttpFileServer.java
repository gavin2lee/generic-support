package com.generic.support.netio.server.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.generic.support.netio.server.IOServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class NettyHttpFileServer implements IOServer {
	private static final Logger log = LoggerFactory.getLogger(NettyHttpFileServer.class);
	private static final int DEFAULT_LISTEN_PORT = 9009;

	@Override
	public void start(int port) throws Exception {
		EventLoopGroup bossLoop = new NioEventLoopGroup();
		EventLoopGroup workerLoop = new NioEventLoopGroup();
		
		try{
			ServerBootstrap server = new ServerBootstrap();
			server.group(bossLoop, workerLoop);
			server.channel(NioServerSocketChannel.class);
			server.childHandler(new NettyHttpFileServerInitializer());
			
			
			ChannelFuture cf = server.bind(port).sync();
			cf.channel().closeFuture().sync();
			
		}finally{
			bossLoop.shutdownGracefully();
			workerLoop.shutdownGracefully();
		}

	}
	
	public static void main(String...args){
		NettyHttpFileServer server = new NettyHttpFileServer();
		try {
			server.start(DEFAULT_LISTEN_PORT);
		} catch (Exception e) {
			log.error("", e);
			System.exit(-1);
		}
	}

	public class NettyHttpFileServerInitializer extends ChannelInitializer<SocketChannel>{

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ch.pipeline().addLast("httpDecoder", new HttpRequestDecoder());
			
			ch.pipeline().addLast("httpAggregator", new HttpObjectAggregator(65535));
			ch.pipeline().addLast("httpEncoder", new HttpResponseEncoder());
		
			ch.pipeline().addLast("httpChunked", new ChunkedWriteHandler());
			ch.pipeline().addLast("fileServerHandler", new NettyHttpFileServerHandler());
			
		}
		
	}
	
	public class NettyHttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest>{

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
			
		}
		
	}

}
