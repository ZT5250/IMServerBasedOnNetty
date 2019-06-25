package the.flash.protocol.response;

import the.flash.protocol.Packet;
import the.flash.protocol.command.Command;

public class QuitGroupResponsePacket extends Packet {

	private String groupId;
	private boolean success;
	private String quitUserName;
	@Override
	public Byte getCommand() {
		return Command.QUITGROUP_RESPONSE;
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
	public String getQuitUserName() {
		return quitUserName;
	}
	public void setQuitUserName(String quitUserName) {
		this.quitUserName = quitUserName;
	}
	@Override
	public String toString() {
		return "[groupId=" + groupId + ", quitUserName=" + quitUserName
				+ "]";
	}
	
}
