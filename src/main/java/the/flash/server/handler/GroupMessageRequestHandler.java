package the.flash.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.group.ChannelGroup;
import the.flash.protocol.request.GroupMessageRequestPacket;
import the.flash.protocol.response.GroupMessageResponsePacket;
import the.flash.util.SessionUtil;

@Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

	public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();
	public GroupMessageRequestHandler() {}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket msg) throws Exception {
		//获取channelGroup
		ChannelGroup channelGroup = SessionUtil.getChannelGroup(msg.getGroupId());

		//构建群聊详细响应实体
		GroupMessageResponsePacket groupRes = new GroupMessageResponsePacket();
		groupRes.setGroupId(msg.getGroupId());
		groupRes.setMessage(msg.getMessage());
		groupRes.setFromUserName(SessionUtil.getSession(ctx.channel()).getUserName());

		//通过channelGroup发送群聊消息
		channelGroup.writeAndFlush(groupRes);
	}

}
