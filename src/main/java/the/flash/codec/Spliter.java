package the.flash.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import the.flash.protocol.PacketCodeC;
import the.flash.server.NettyServer;

public class Spliter extends LengthFieldBasedFrameDecoder {

	public Spliter(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		if (in.getInt(in.readerIndex()) != PacketCodeC.MAGIC_NUMBER) {
            ctx.channel().close();
            System.out.println(ctx.channel().attr(NettyServer.clientKey).get() + "不符合要求的协议！");
            return null;
        }

        return super.decode(ctx, in);
	}

}
