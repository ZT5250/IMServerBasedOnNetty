package the.flash.client.console;

import java.util.Scanner;

import io.netty.channel.Channel;
import the.flash.protocol.request.GroupMessageRequestPacket;

public class SendToGroupConsoleCommand implements ConsoleCommand {

	@Override
	public void exec(Scanner scanner, Channel channel) {
		System.out.println("请输入发送消息的群组id：");
		String groupId = scanner.next();
		System.out.print(">>");
		GroupMessageRequestPacket gmreq = new GroupMessageRequestPacket();
		gmreq.setGroupId(groupId);
		String message = scanner.next();
		gmreq.setMessage(message);
		channel.writeAndFlush(gmreq);
	}

}
