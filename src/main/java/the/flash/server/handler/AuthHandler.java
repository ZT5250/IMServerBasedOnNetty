package the.flash.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import the.flash.server.NettyServer;
import the.flash.util.SessionUtil;

@Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {

	public static final AuthHandler INSTANCE = new AuthHandler();
	public AuthHandler() {
	}
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().attr(NettyServer.clientKey).get() + "AuthHandler added : 登录校验hanlder已添加");
		super.handlerAdded(ctx);
	}


	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (SessionUtil.hasLogin(ctx.channel())) {
			//已经登录, 可以放行
			//手动移除当前handler, 通过验证之后, 此登录鉴权handler无需在使用
			ctx.pipeline().remove(this);
			super.channelRead(ctx, msg);
		} else {
			//未登录,主动关掉连接
			ctx.channel().close();
		}
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		if (SessionUtil.hasLogin(ctx.channel())) {
			System.out.println(ctx.channel().attr(NettyServer.clientKey).get() + "已经通过登录验证, 验证逻辑无需再次执行, 将AuthHandler移除");
			super.handlerRemoved(ctx);
		} else {
			System.out.println(ctx.channel().attr(NettyServer.clientKey).get() + "未登录，强制关闭连接!");
		}
	}
}
