package the.flash.protocol.response;

import the.flash.protocol.Packet;
import the.flash.protocol.command.Command;

public class HeartBeatResponsePacket extends Packet {

	@Override
	public Byte getCommand() {
		return Command.HEARTBEAT_RESPONSE;
	}

}
