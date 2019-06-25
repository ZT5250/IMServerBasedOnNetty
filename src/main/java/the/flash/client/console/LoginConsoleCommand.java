package the.flash.client.console;

import java.util.Scanner;

import io.netty.channel.Channel;
import the.flash.protocol.request.LoginRequestPacket;

public class LoginConsoleCommand implements ConsoleCommand {

	@Override
	public void exec(Scanner scanner, Channel channel) {
		LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        System.out.print("输入用户名登录: ");
        loginRequestPacket.setUsername(scanner.nextLine());
        loginRequestPacket.setPassword("admin");

        // 发送登录数据包
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
	}
	private static void waitForLoginResponse() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }
    }
}
