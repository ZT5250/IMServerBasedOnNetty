package the.flash.redisClient;

import java.util.Scanner;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.redis.RedisArrayAggregator;
import io.netty.handler.codec.redis.RedisBulkStringAggregator;
import io.netty.handler.codec.redis.RedisDecoder;
import io.netty.handler.codec.redis.RedisEncoder;
import the.flash.redisClient.handler.RedisClientHandler;
import the.flash.util.ClientSessionUtil;

public class NettyClient {

	private String host;
	private int port;
	public NettyClient(String host, int port) {
		this.host = host;
		this.port = port;
	}
	public void init() {
		NioEventLoopGroup worker = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(worker)
		.channel(NioSocketChannel.class)
		.option(ChannelOption.TCP_NODELAY, true)
		.option(ChannelOption.SO_KEEPALIVE, true)
		.handler(new ChannelInitializer<NioSocketChannel>() {

			@Override
			protected void initChannel(NioSocketChannel ch) throws Exception {
				ch.pipeline()
				.addLast(new RedisDecoder())
		        .addLast(new RedisBulkStringAggregator())
		        .addLast(new RedisArrayAggregator())
		        .addLast(new RedisEncoder())
		        .addLast(new RedisClientHandler());
			}
		});
		b.connect(host, port).addListener(f -> {
			if (f.isSuccess()) {
				Channel channel = ((ChannelFuture) f).channel();
				Scanner scanner = new Scanner(System.in);
				new Thread(() -> {
		            while (!Thread.interrupted()) {
						channel.writeAndFlush(scanner.nextLine());
						//channel.writeAndFlush(RedisUtils.getCommond(scanner.nextLine()));
		            }
		        }).start();
			}
		});
	}
	public static void main(String[] args) {
		NettyClient nclient = new NettyClient("localhost", 6379);
		nclient.init();
	}
}
