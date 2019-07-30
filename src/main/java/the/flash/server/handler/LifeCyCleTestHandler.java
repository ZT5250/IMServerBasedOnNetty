package the.flash.server.handler;

import java.util.UUID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import the.flash.server.NettyServer;
import the.flash.util.SessionUtil;
import io.netty.channel.ChannelHandler.Sharable;

@Sharable
public class LifeCyCleTestHandler extends ChannelInboundHandlerAdapter {
	public static final LifeCyCleTestHandler INSTANCE = new LifeCyCleTestHandler();
	public LifeCyCleTestHandler() {}
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		//为每一个channel(即客户端)设置一个唯一标识, 后面登录成功之后直接做为userid返回给客户端
		String serverGenerateUUID = UUID.randomUUID().toString() + "-" + System.currentTimeMillis() + "_";
		ctx.channel().attr(NettyServer.clientKey).set(serverGenerateUUID);
		System.out.println(ctx.channel().attr(NettyServer.clientKey).get() + "逻辑处理器被添加：handlerAdded()");
		super.handlerAdded(ctx);
	}
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().attr(NettyServer.clientKey).get() + "channel 绑定到线程(NioEventLoop)：channelRegistered()");
		super.channelRegistered(ctx);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().attr(NettyServer.clientKey).get() + "channel 准备就绪：channelActive() " + NettyServer.channelCounter.incrementAndGet());
		super.channelActive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//System.out.println(ctx.channel().attr(NettyServer.clientKey).get() + "channel 有数据可读：channelRead()");
		super.channelRead(ctx, msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		//System.out.println(ctx.channel().attr(NettyServer.clientKey).get() + "channel 某次数据读完：channelReadComplete()");
		super.channelReadComplete(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().attr(NettyServer.clientKey).get() + "channel 被关闭：channelInactive() " + NettyServer.channelCounter.decrementAndGet());
		SessionUtil.unbindSession(ctx.channel());
		super.channelInactive(ctx);
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().attr(NettyServer.clientKey).get() + "channel 取消线程(NioEventLoop) 的绑定: channelUnregistered()");
		super.channelUnregistered(ctx);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().attr(NettyServer.clientKey).get() + "逻辑处理器被移除：handlerRemoved()");
		super.handlerRemoved(ctx);
	}
}
