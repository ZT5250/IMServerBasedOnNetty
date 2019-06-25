package the.flash.client.console;

import java.util.Scanner;

import io.netty.channel.Channel;
import the.flash.protocol.request.ListGroupMembersRequestPacket;

public class ListGroupMembersConsoleCommand implements ConsoleCommand {

	@Override
	public void exec(Scanner scanner, Channel channel) {
		ListGroupMembersRequestPacket lgmreq = new ListGroupMembersRequestPacket();

		System.out.print("输入 groupId，获取成员列表：");
        String groupId = scanner.next();
		lgmreq.setGroupId(groupId);
		channel.writeAndFlush(lgmreq);
	}

}
