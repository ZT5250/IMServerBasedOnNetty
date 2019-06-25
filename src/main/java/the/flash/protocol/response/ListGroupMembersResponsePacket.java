package the.flash.protocol.response;

import java.util.List;

import the.flash.dto.Session;
import the.flash.protocol.Packet;
import the.flash.protocol.command.Command;

public class ListGroupMembersResponsePacket extends Packet {

	private String groupId;
	private List<Session> sessionList;
	@Override
	public Byte getCommand() {
		return Command.LISTGROUPMEMBERS_RESPONSE;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public List<Session> getSessionList() {
		return sessionList;
	}
	public void setSessionList(List<Session> sessionList) {
		this.sessionList = sessionList;
	}

}
