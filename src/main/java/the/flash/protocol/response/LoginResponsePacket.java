package the.flash.protocol.response;

import static the.flash.protocol.command.Command.LOGIN_RESPONSE;

import the.flash.protocol.Packet;

public class LoginResponsePacket extends Packet {
	private String userId;

    private String userName;
	private boolean success;

    private String reason;
	@Override
	public Byte getCommand() {
		return LOGIN_RESPONSE;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
