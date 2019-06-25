package the.flash.protocol.response;

import the.flash.protocol.Packet;
import static the.flash.protocol.command.Command.MESSAGE_RESPONSE;

public class MessageResponsePacket extends Packet {

	private String fromUserId;
	private String fromUserName;
	private String message;

	@Override
	public Byte getCommand() {
		return MESSAGE_RESPONSE;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

}
