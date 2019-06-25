package the.flash.server.handler;


import org.apache.commons.lang3.StringUtils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.SimpleChannelInboundHandler;
import the.flash.dto.Session;
import the.flash.protocol.request.MessageRequestPacket;
import the.flash.protocol.response.MessageResponsePacket;
import the.flash.server.NettyServer;
import the.flash.util.SessionUtil;

@Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

	public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();
	public MessageRequestHandler() {
	}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
		// 处理消息
		System.out.println(ctx.channel().attr(NettyServer.clientKey).get() + " >> " + messageRequestPacket.getToUserId() + ":" + messageRequestPacket.getMessage());
		if (StringUtils.isNotBlank(messageRequestPacket.getToUserId())) {
			//找到目标channel,并将消息发送过去
			Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());
			if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
				Session s = SessionUtil.getSession(ctx.channel());
				MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
				messageResponsePacket.setMessage(messageRequestPacket.getMessage());
				messageResponsePacket.setFromUserId(s.getUserId());
				messageResponsePacket.setFromUserName(s.getUserName());
				toUserChannel.writeAndFlush(messageResponsePacket);
			} else {
				MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
				messageResponsePacket.setMessage("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败!");
				messageResponsePacket.setFromUserId("admin");
				messageResponsePacket.setFromUserName("系统管理员");
				ctx.channel().writeAndFlush(messageResponsePacket);
	        }
		}
	}

}
