package the.flash.protocol.request;
import static the.flash.protocol.command.Command.LOGIN_REQUEST;

import the.flash.protocol.Packet;
public class LoginRequestPacket extends Packet {

	private String userId;

	private String username;

	private String password;

	@Override
	public Byte getCommand() {
		return LOGIN_REQUEST;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
