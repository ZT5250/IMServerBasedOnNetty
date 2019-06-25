package the.flash.protocol.request;

import the.flash.protocol.Packet;
import the.flash.protocol.command.Command;

public class JoinGroupRequestPacket extends Packet {

	private String groupId;
	private boolean success;
	@Override
	public Byte getCommand() {
		return Command.JOINGROUP_REQUEST;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

}
