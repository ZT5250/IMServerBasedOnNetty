package the.flash.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import the.flash.server.NettyServer;

public class ClientLifeCyCleTestHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().attr(NettyServer.clientKey).get() + "ClientLifeCyCleTestHandler 逻辑处理器被添加：handlerAdded()");
		super.handlerAdded(ctx);
	}
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().attr(NettyServer.clientKey).get() + "ClientLifeCyCleTestHandler channel 绑定到线程(NioEventLoop)：channelRegistered()");
		super.channelRegistered(ctx);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.channel().attr(NettyServer.clientKey).set(ctx.channel().localAddress().toString() + "_");
		System.out.println(ctx.channel().attr(NettyServer.clientKey).get() + "ClientLifeCyCleTestHandler channel 准备就绪：channelActive()");
		super.channelActive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//System.out.println(ctx.channel().attr(NettyServer.clientKey).get() + "ClientLifeCyCleTestHandler channel 有数据可读：channelRead()");
		super.channelRead(ctx, msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		//System.out.println(ctx.channel().attr(NettyServer.clientKey).get() + "ClientLifeCyCleTestHandler channel 某次数据读完：channelReadComplete()");
		super.channelReadComplete(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().attr(NettyServer.clientKey).get() + "ClientLifeCyCleTestHandler channel 被关闭：channelInactive()");
		super.channelInactive(ctx);
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().attr(NettyServer.clientKey).get() + "ClientLifeCyCleTestHandler channel 取消线程(NioEventLoop) 的绑定: channelUnregistered()");
		super.channelUnregistered(ctx);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().attr(NettyServer.clientKey).get() + "ClientLifeCyCleTestHandler 逻辑处理器被移除：handlerRemoved()");
		super.handlerRemoved(ctx);
	}
}
