package com.generic.support.netio.client.netty;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.generic.support.netio.client.AbstractMultipleIOClient;
import com.generic.support.netio.coder.MessagePackClientDecoder;
import com.generic.support.netio.coder.MessagePackEncoder;
import com.generic.support.netio.dto.LoginRequest;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class NettyLoginOnceClient extends AbstractMultipleIOClient {
	private static final Logger log = LoggerFactory.getLogger(NettyLoginOnceClient.class);
	private static final AtomicLong totalSendAmount = new AtomicLong();
	private static final AtomicLong totalReceiveAmount = new AtomicLong();
	private static final AtomicInteger clientAmount = new AtomicInteger();

	public NettyLoginOnceClient(String host, int port, int times) {
		super(host, port, times);
		int index = clientAmount.incrementAndGet();
		log.debug(NettyLoginOnceClient.class.getSimpleName() + " - " + index + " - " + " initializing...");
	}

	@Override
	public void kickoff() throws Exception {
		log.debug(String.format("%s  kickoff with host:%s,port:%d", NettyLoginOnceClient.class.getSimpleName(), getHost(),
				getPort()));
		EventLoopGroup clientGroup = new NioEventLoopGroup();

		try {
			Bootstrap client = new Bootstrap();
			client.group(clientGroup);

			client.channel(NioSocketChannel.class);
			client.option(ChannelOption.TCP_NODELAY, true);
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
			ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2,0,2));

			ch.pipeline().addLast("messagePackDecoder", new MessagePackClientDecoder());
			
			ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
			
			ch.pipeline().addLast("messagePackEncoder", new MessagePackEncoder());

			ch.pipeline().addLast(new NettyLoginClientHandler());
		}

	}

	public class NettyLoginClientHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			log.debug("channelActive");
			while(sendRequest(ctx)){
				
			};
			ctx.flush();
			super.channelActive(ctx);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			log.debug("channelRead");
			log.debug("recv type:" + msg.getClass().getName());
			log.debug(String.format("%s RECV %s <<< : %s", Thread.currentThread().getName(), totalReceiveAmount.incrementAndGet(), msg));
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			log.debug("channelReadComplete");
//			sendRequest(ctx);
//			ctx.flush();
			
			if(totalReceiveAmount.get() >= getTimes()){
				log.warn(String.format("%s - recv amount:%d - maxtimes:%d - going to close.",
						Thread.currentThread().getName(), totalReceiveAmount.get(), getTimes()));
				ctx.flush();
				ctx.disconnect();
				ctx.close();
			}
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			log.error(cause.getMessage(), cause);
			ctx.close();
		}

		protected boolean sendRequest(ChannelHandlerContext ctx) {
			
			
			

			long amount = totalSendAmount.get();
			while ( amount < getTimes()) {
				if(totalSendAmount.compareAndSet(amount, ++amount)){
//					amount++;
					LoginRequest req = new LoginRequest();
					req.setUsername("test-"+amount);
					req.setPassword(String.valueOf(System.nanoTime()));
					req.setClientIdentity(Thread.currentThread().getName());
					
					ctx.write(req);
					log.debug(String.format("%s SEND %s >>> : %s", Thread.currentThread().getName(), amount, req));
					return true;
				}else{
					amount = totalSendAmount.get();
					continue;
				}
				
			}
			
			return false;

		}

	}

}
