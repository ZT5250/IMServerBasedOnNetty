package the.flash.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import the.flash.protocol.response.CreateGroupResponsePacket;

public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket cgres) throws Exception {
		System.out.print("群创建成功，id 为[" + cgres.getGroupId() + "], ");
        System.out.println("群里面有：" + cgres.getGroupUserSession());
	}

}
