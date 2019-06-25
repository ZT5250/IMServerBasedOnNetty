package the.flash.server.handler;

import org.apache.commons.lang3.StringUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.group.ChannelGroup;
import the.flash.dto.Session;
import the.flash.protocol.request.JoinGroupRequestPacket;
import the.flash.protocol.response.JoinGroupResponsePacket;
import the.flash.util.SessionUtil;

@Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {

	public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();
	public JoinGroupRequestHandler() {}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket msg) throws Exception {
		JoinGroupResponsePacket res = new JoinGroupResponsePacket();
		res.setSuccess(false);
		if (StringUtils.isBlank(msg.getGroupId())) {
			res.setReason("groupid 不能为空！");
			ctx.channel().writeAndFlush(res);
			return;
		} else {
			ChannelGroup chatGroupChannel = SessionUtil.getChannelGroup(msg.getGroupId());
			if (chatGroupChannel == null) {
				res.setReason("指定的group【" + msg.getGroupId() + "】不存在！");
				ctx.channel().writeAndFlush(res);
				return;
			}
			chatGroupChannel.add(ctx.channel());
			res.setGroupId(msg.getGroupId());
			res.setSuccess(true);
			Session newJoiner = SessionUtil.getSession(ctx.channel());
			res.setReason("【" +newJoiner.getUserName()+ "】加入群聊");
			chatGroupChannel.writeAndFlush(res);
		}
	}

}
