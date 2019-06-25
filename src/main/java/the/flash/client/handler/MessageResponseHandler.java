package the.flash.client.handler;

import java.util.Date;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import the.flash.protocol.response.MessageResponsePacket;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) throws Exception {
		System.out.println(new Date() + ": " +messageResponsePacket.getFromUserId() + "-"+messageResponsePacket.getFromUserName()
		+ " >> : " + messageResponsePacket.getMessage());
	}

}
