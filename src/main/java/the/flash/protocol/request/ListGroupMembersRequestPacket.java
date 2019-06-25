package the.flash.protocol.request;

import the.flash.protocol.Packet;
import the.flash.protocol.command.Command;

public class ListGroupMembersRequestPacket extends Packet {

	private String groupId;
	@Override
	public Byte getCommand() {
		return Command.LISTGROUPMEMBERS_REQUEST;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
