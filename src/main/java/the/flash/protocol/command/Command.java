package the.flash.protocol.command;

public interface Command {
	Byte LOGIN_REQUEST = 1;
	Byte LOGIN_RESPONSE = 2;

	Byte MESSAGE_REQUEST = 3;
	Byte MESSAGE_RESPONSE = 4;

	Byte CREATEGROUP_REQUEST = 5;
	Byte CREATEGROUP_RESPONSE = 6;

	Byte LOGOUT_REQUEST = 7;
	Byte LOGOUT_RESPONSE = 8;

	Byte JOINGROUP_REQUEST = 9;
	Byte JOINGROUP_RESPONSE = 10;

	Byte QUITGROUP_REQUEST = 11;
	Byte QUITGROUP_RESPONSE = 12;

	Byte LISTGROUPMEMBERS_REQUEST = 13;
	Byte LISTGROUPMEMBERS_RESPONSE = 14;

	Byte GROUPMESSAGE_REQUEST = 15;
	Byte GROUPMESSAGE_RESPONSE = 16;
}
