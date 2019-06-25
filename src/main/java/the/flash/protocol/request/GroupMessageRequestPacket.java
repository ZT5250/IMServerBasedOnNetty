package the.flash.protocol.request;

import the.flash.protocol.Packet;
import the.flash.protocol.command.Command;

public class GroupMessageRequestPacket extends Packet {

	private String groupId;
	private String message;
	@Override
	public Byte getCommand() {
		return Command.GROUPMESSAGE_REQUEST;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
