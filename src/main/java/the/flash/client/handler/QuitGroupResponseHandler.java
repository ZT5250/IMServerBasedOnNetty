package the.flash.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import the.flash.protocol.response.QuitGroupResponsePacket;

public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket msg) throws Exception {
		if (msg.isSuccess()) {
			System.out.println("【" +msg.getQuitUserName()+ "】退出群聊【" +msg.getGroupId()+ "】！");
		} else {
			System.out.println("退出群聊【" +msg.getGroupId()+ "】失败！");
		}
	}

}
