package com.generic.support.netio.server.netty;

import com.generic.support.netio.server.IOServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyLoginServer implements IOServer {

	@Override
	public void start(int port) throws Exception {
		EventLoopGroup bossGrp = new NioEventLoopGroup();
		EventLoopGroup workerGrp = new NioEventLoopGroup();
		
		try{
			ServerBootstrap server = new ServerBootstrap();
			server.group(bossGrp, workerGrp);
			server.channel(NioServerSocketChannel.class);
			server.childHandler(new NettyLoginChannelInitializer());
			
			ChannelFuture cf = server.bind(port).sync();
			cf.channel().closeFuture().sync();
			
		}finally{
			bossGrp.shutdownGracefully();
			workerGrp.shutdownGracefully();
		}

	}
	
	public class NettyLoginChannelInitializer extends ChannelInitializer<SocketChannel>{

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			
		}
		
	}

}
