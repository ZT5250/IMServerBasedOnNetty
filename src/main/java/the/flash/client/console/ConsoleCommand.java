package the.flash.client.console;

import java.util.Scanner;

import io.netty.channel.Channel;

public interface ConsoleCommand {
	void exec(Scanner scanner, Channel channel);
}
