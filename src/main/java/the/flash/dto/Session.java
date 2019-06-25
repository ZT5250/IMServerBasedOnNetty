package the.flash.dto;

public class Session {

	private String userId;
	private String userName;
	public Session(String userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
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
	@Override
	public String toString() {
		return "{\"userId\":\"" + userId + "\", \"userName\":\"" + userName + "\"}";
	}
}
