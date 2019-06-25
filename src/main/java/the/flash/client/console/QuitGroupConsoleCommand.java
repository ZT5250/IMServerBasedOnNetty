package the.flash.client.console;

import java.util.Scanner;

import io.netty.channel.Channel;
import the.flash.protocol.request.QuitGroupRequestPacket;

public class QuitGroupConsoleCommand implements ConsoleCommand {

	@Override
	public void exec(Scanner scanner, Channel channel) {
		QuitGroupRequestPacket qgreq = new QuitGroupRequestPacket();
		System.out.print("输入 想要退出的群聊ID ：");
        String groupId = scanner.next();

        qgreq.setGroupId(groupId);
        channel.writeAndFlush(qgreq);
	}

}
