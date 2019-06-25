package the.flash.protocol.request;

import the.flash.protocol.Packet;
import the.flash.protocol.command.Command;

public class QuitGroupRequestPacket extends Packet {

	private String groupId;
	@Override
	public Byte getCommand() {
		return Command.QUITGROUP_REQUEST;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}
