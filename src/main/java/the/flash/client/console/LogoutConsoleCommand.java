package the.flash.client.console;

import java.util.Scanner;

import io.netty.channel.Channel;
import the.flash.protocol.request.LogoutRequestPacket;

public class LogoutConsoleCommand implements ConsoleCommand {

	@Override
	public void exec(Scanner scanner, Channel channel) {
		LogoutRequestPacket logoutRequestPacket = new LogoutRequestPacket();
        channel.writeAndFlush(logoutRequestPacket);
	}

}
