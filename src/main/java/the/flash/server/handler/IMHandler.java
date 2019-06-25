package the.flash.server.handler;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.SimpleChannelInboundHandler;
import the.flash.protocol.Packet;
import the.flash.protocol.command.Command;

@Sharable
public class IMHandler extends SimpleChannelInboundHandler<Packet> {
	public static final IMHandler INSTANCE = new IMHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;
    public IMHandler () {
    	handlerMap = new HashMap<>();

        handlerMap.put(Command.MESSAGE_REQUEST, MessageRequestHandler.INSTANCE);
        handlerMap.put(Command.CREATEGROUP_REQUEST, CreateGroupRequestHanlder.INSTANCE);
        handlerMap.put(Command.JOINGROUP_REQUEST, JoinGroupRequestHandler.INSTANCE);
        handlerMap.put(Command.QUITGROUP_REQUEST, QuitGroupRequestHandler.INSTANCE);
        handlerMap.put(Command.LISTGROUPMEMBERS_REQUEST, ListGroupMembersRequestHandler.INSTANCE);
        handlerMap.put(Command.GROUPMESSAGE_REQUEST, GroupMessageRequestHandler.INSTANCE);
    }
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
		handlerMap.get(msg.getCommand()).channelRead(ctx, msg);
	}

}
