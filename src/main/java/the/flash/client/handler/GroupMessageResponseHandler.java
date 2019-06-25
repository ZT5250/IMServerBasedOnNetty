package the.flash.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import the.flash.protocol.response.GroupMessageResponsePacket;

public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket msg) throws Exception {
		System.out.println("收到群【" +msg.getGroupId()+ "】中【" +msg.getFromUserName()+ "】的消息:" + msg.getMessage());
	}

}
