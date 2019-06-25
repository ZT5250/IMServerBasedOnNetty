package the.flash.protocol.response;

import the.flash.protocol.Packet;
import the.flash.protocol.command.Command;

public class GroupMessageResponsePacket extends Packet {

	private String groupId;
	private String message;
	private String fromUserName;
	@Override
	public Byte getCommand() {
		return Command.GROUPMESSAGE_RESPONSE;
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
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

}
