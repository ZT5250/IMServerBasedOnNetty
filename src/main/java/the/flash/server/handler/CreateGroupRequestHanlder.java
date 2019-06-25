package the.flash.server.handler;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import the.flash.dto.Session;
import the.flash.protocol.request.CreateGroupRequestPacket;
import the.flash.protocol.response.CreateGroupResponsePacket;
import the.flash.util.SessionUtil;


@Sharable
public class CreateGroupRequestHanlder extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

	public static final CreateGroupRequestHanlder INSTANCE = new CreateGroupRequestHanlder();
	public CreateGroupRequestHanlder() {}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) throws Exception {
		//创建一个channel分组
		ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
		//找出拉取的用户对应的channel信息以及session信息
		List<Session> userSessions = msg.getUserIdList().stream()
		.filter(uid -> SessionUtil.getChannel(uid) != null)
		.map(uid -> {
			//将在线用户的channel添加到channel分组中
			Channel onlineUserChannel = SessionUtil.getChannel(uid);
			channelGroup.add(onlineUserChannel);
			//返回在线用户的名称
			return SessionUtil.getSession(onlineUserChannel);
		}).collect(Collectors.toList());
		String groupId = UUID.randomUUID().toString();
		//构建群组创建成功响应
		CreateGroupResponsePacket cgres = new CreateGroupResponsePacket();
		cgres.setGroupId(groupId);
		cgres.setGroupUserSession(userSessions);
		cgres.setSuccess(true);

		//通过channelgroup通知所有群里的人，群创建成功。
		channelGroup.writeAndFlush(cgres);
		System.out.print("群创建成功，id 为[" + cgres.getGroupId() + "], ");
        System.out.println("群里面有：" + cgres.getGroupUserSession());

        //记录channelgroup信息
        SessionUtil.bindChannelGroup(groupId, channelGroup);
	}

}
