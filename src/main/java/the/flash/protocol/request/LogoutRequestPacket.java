package the.flash.protocol.request;

import the.flash.protocol.Packet;
import the.flash.protocol.command.Command;

public class LogoutRequestPacket extends Packet {

	@Override
	public Byte getCommand() {
		return Command.LOGOUT_REQUEST;
	}

}
