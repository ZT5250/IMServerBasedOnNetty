package the.flash.protocol.response;

import the.flash.protocol.Packet;
import the.flash.protocol.command.Command;

public class JoinGroupResponsePacket extends Packet {

	private String groupId;
	private boolean success;
	private String reason;
	@Override
	public Byte getCommand() {
		return Command.JOINGROUP_RESPONSE;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}
