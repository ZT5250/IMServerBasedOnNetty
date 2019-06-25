package the.flash.codec;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToMessageCodec;
import the.flash.protocol.Packet;
import the.flash.protocol.PacketCodeC;

@Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet> {

	public static final PacketCodecHandler INSTANCE = new PacketCodecHandler();
	public PacketCodecHandler() {}
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
		out.add(PacketCodeC.INSTANCE.decode(byteBuf));
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> out) throws Exception {
		ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
		PacketCodeC.INSTANCE.encode(byteBuf, packet);
        out.add(byteBuf);
	}


}
