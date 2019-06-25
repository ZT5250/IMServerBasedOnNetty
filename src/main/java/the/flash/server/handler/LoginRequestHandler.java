package the.flash.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.SimpleChannelInboundHandler;
import the.flash.dto.Session;
import the.flash.protocol.request.LoginRequestPacket;
import the.flash.protocol.response.LoginResponsePacket;
import the.flash.server.NettyServer;
import the.flash.util.SessionUtil;

@Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

	public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    protected LoginRequestHandler() {
    }
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
		// 登录校验
		LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
		loginResponsePacket.setVersion(loginRequestPacket.getVersion());
		String serverGenerateUUID = ctx.channel().attr(NettyServer.clientKey).get();
		if (valid(loginRequestPacket)) {
			loginResponsePacket.setSuccess(true);
			loginResponsePacket.setUserId(serverGenerateUUID);
			loginResponsePacket.setUserName(loginRequestPacket.getUsername());
			//将当前channel标记为已登录
			SessionUtil.bindSession(new Session(serverGenerateUUID, loginRequestPacket.getUsername()), ctx.channel());
		} else {
			loginResponsePacket.setReason("账号密码校验失败");
			loginResponsePacket.setSuccess(false);
		}
		// 登录响应
		ctx.channel().writeAndFlush(loginResponsePacket);
	}
	private boolean valid(LoginRequestPacket loginRequestPacket) {
		if (loginRequestPacket == null || !"admin".equalsIgnoreCase(loginRequestPacket.getPassword())) {
			return false;
		}
		return true;
	}
}
