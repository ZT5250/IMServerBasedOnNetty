package the.flash.protocol.response;

import java.util.List;

import the.flash.dto.Session;
import the.flash.protocol.Packet;
import the.flash.protocol.command.Command;

public class CreateGroupResponsePacket extends Packet {

	private String groupId;
	private List<Session> groupUserSession;
	private boolean success;
	@Override
	public Byte getCommand() {
		return Command.CREATEGROUP_RESPONSE;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public List<Session> getGroupUserSession() {
		return groupUserSession;
	}
	public void setGroupUserSession(List<Session> groupUserSession) {
		this.groupUserSession = groupUserSession;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

}
