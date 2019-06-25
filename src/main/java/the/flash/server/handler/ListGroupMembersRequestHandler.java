package the.flash.server.handler;

import java.util.List;
import java.util.stream.Collectors;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import the.flash.dto.Session;
import the.flash.protocol.request.ListGroupMembersRequestPacket;
import the.flash.protocol.response.ListGroupMembersResponsePacket;
import the.flash.util.SessionUtil;

@Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

	public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();
	public ListGroupMembersRequestHandler() {}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket msg) throws Exception {
		//获取群组channelGroup
		String groupId = msg.getGroupId();
		ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
		//遍历群成员session信息
		List<Session> groupMembersSession = channelGroup.stream().map(c -> {
			return SessionUtil.getSession(c);
		}).collect(Collectors.toList());
		//构建群组信息响应实体
		ListGroupMembersResponsePacket lgmres = new ListGroupMembersResponsePacket();
		lgmres.setGroupId(groupId);
		lgmres.setSessionList(groupMembersSession);
		//回复响应
		ctx.channel().writeAndFlush(lgmres);
	}

}
