package the.flash.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import the.flash.protocol.request.QuitGroupRequestPacket;
import the.flash.protocol.response.QuitGroupResponsePacket;
import the.flash.util.SessionUtil;

@Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {

	public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();
	public QuitGroupRequestHandler() {}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket msg) throws Exception {
		//根据groupid获取channelgroup
		ChannelGroup chatChannelGroup = SessionUtil.getChannelGroup(msg.getGroupId());
		//将当前channle从channelGroup中移除
		chatChannelGroup.remove(ctx.channel());
		//构建退群响应通知
		QuitGroupResponsePacket qgres = new QuitGroupResponsePacket();
		qgres.setGroupId(msg.getGroupId());
		qgres.setSuccess(true);
		qgres.setQuitUserName(SessionUtil.getSession(ctx.channel()).getUserName());
		//如果channelGroup中没有channel了则关闭当前channelGroup
		if (chatChannelGroup.isEmpty()) {
			ctx.channel().writeAndFlush(qgres);
			chatChannelGroup.close();
			SessionUtil.unbindChannelGroup(msg.getGroupId());
			return;
		}
		chatChannelGroup.writeAndFlush(qgres);
	}

}
