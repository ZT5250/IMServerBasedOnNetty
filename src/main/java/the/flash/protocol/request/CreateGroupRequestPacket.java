package the.flash.protocol.request;

import java.util.List;

import the.flash.protocol.Packet;
import the.flash.protocol.command.Command;

public class CreateGroupRequestPacket extends Packet {

	private List<String> userIdList;
	@Override
	public Byte getCommand() {
		return Command.CREATEGROUP_REQUEST;
	}
	public List<String> getUserIdList() {
		return userIdList;
	}
	public void setUserIdList(List<String> userIdList) {
		this.userIdList = userIdList;
	}

}
