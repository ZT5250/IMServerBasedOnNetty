package the.flash.protocol.request;

import the.flash.protocol.Packet;
import static the.flash.protocol.command.Command.MESSAGE_REQUEST;

public class MessageRequestPacket extends Packet {

	private String toUserId;
	private String message;

	public MessageRequestPacket() {
		super();
	}

	public MessageRequestPacket(String toUserId, String message) {
		super();
		this.toUserId = toUserId;
		this.message = message;
	}

	@Override
	public Byte getCommand() {
		return MESSAGE_REQUEST;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

}
