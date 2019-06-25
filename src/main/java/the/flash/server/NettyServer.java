package the.flash.server;


import java.util.concurrent.atomic.AtomicInteger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import the.flash.codec.PacketCodecHandler;
import the.flash.codec.Spliter;
import the.flash.server.handler.AuthHandler;
import the.flash.server.handler.IMHandler;
import the.flash.server.handler.LifeCyCleTestHandler;
import the.flash.server.handler.LoginRequestHandler;

public class NettyServer {

    private static final int BEGIN_PORT = 8000;
    public static final AttributeKey<String> clientKey = AttributeKey.newInstance("clientKey");
    public static final AttributeKey<String> clientName = AttributeKey.newInstance("clientName");
    public static final AtomicInteger channelCounter = new AtomicInteger(0);
    public static void main(String[] args) {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        super.channelActive(ctx);
                    }
                })
                .attr(AttributeKey.newInstance("serverName"), "nettyServer")
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        System.out.println(ch.attr(clientKey).get());
                        ch.pipeline()
                        .addLast(new LifeCyCleTestHandler())
                        .addLast(new Spliter(Integer.MAX_VALUE, 7, 4))
                        .addLast(PacketCodecHandler.INSTANCE)
                        .addLast(LoginRequestHandler.INSTANCE)
                        .addLast(AuthHandler.INSTANCE)
                        .addLast(IMHandler.INSTANCE);
                    }
                });


        bind(serverBootstrap, BEGIN_PORT);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
                bind(serverBootstrap, port + 1);
            }
        });
    }
}
