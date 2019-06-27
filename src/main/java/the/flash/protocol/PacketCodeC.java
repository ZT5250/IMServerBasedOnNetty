package the.flash.protocol;

import io.netty.buffer.ByteBuf;
import the.flash.protocol.command.Command;
import the.flash.protocol.request.CreateGroupRequestPacket;
import the.flash.protocol.request.GroupMessageRequestPacket;
import the.flash.protocol.request.HeartBeatRequestPacket;
import the.flash.protocol.request.JoinGroupRequestPacket;
import the.flash.protocol.request.ListGroupMembersRequestPacket;
import the.flash.protocol.request.LoginRequestPacket;
import the.flash.protocol.request.MessageRequestPacket;
import the.flash.protocol.request.QuitGroupRequestPacket;
import the.flash.protocol.response.CreateGroupResponsePacket;
import the.flash.protocol.response.GroupMessageResponsePacket;
import the.flash.protocol.response.HeartBeatResponsePacket;
import the.flash.protocol.response.JoinGroupResponsePacket;
import the.flash.protocol.response.ListGroupMembersResponsePacket;
import the.flash.protocol.response.LoginResponsePacket;
import the.flash.protocol.response.MessageResponsePacket;
import the.flash.protocol.response.QuitGroupResponsePacket;
import the.flash.serialize.Serializer;
import the.flash.serialize.impl.JSONSerializer;

import java.util.HashMap;
import java.util.Map;

import static the.flash.protocol.command.Command.LOGIN_REQUEST;
import static the.flash.protocol.command.Command.LOGIN_RESPONSE;
import static the.flash.protocol.command.Command.MESSAGE_REQUEST;
import static the.flash.protocol.command.Command.MESSAGE_RESPONSE;
import static the.flash.protocol.command.Command.CREATEGROUP_REQUEST;
import static the.flash.protocol.command.Command.CREATEGROUP_RESPONSE;
import static the.flash.protocol.command.Command.JOINGROUP_REQUEST;
import static the.flash.protocol.command.Command.JOINGROUP_RESPONSE;
import static the.flash.protocol.command.Command.QUITGROUP_REQUEST;
import static the.flash.protocol.command.Command.QUITGROUP_RESPONSE;
import static the.flash.protocol.command.Command.LISTGROUPMEMBERS_REQUEST;
import static the.flash.protocol.command.Command.LISTGROUPMEMBERS_RESPONSE;
import static the.flash.protocol.command.Command.GROUPMESSAGE_REQUEST;
import static the.flash.protocol.command.Command.GROUPMESSAGE_RESPONSE;

public class PacketCodeC {

	public static final int MAGIC_NUMBER = 0x1234567;
	public static final PacketCodeC INSTANCE = new PacketCodeC();
	private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
	private static final Map<Byte, Serializer> serializerMap;
	static {
		packetTypeMap = new HashMap<>();
		packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
		packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
		packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
		packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);
		packetTypeMap.put(CREATEGROUP_REQUEST, CreateGroupRequestPacket.class);
		packetTypeMap.put(CREATEGROUP_RESPONSE, CreateGroupResponsePacket.class);
		packetTypeMap.put(JOINGROUP_REQUEST, JoinGroupRequestPacket.class);
		packetTypeMap.put(JOINGROUP_RESPONSE, JoinGroupResponsePacket.class);
		packetTypeMap.put(QUITGROUP_REQUEST, QuitGroupRequestPacket.class);
		packetTypeMap.put(QUITGROUP_RESPONSE, QuitGroupResponsePacket.class);
		packetTypeMap.put(LISTGROUPMEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
		packetTypeMap.put(LISTGROUPMEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);
		packetTypeMap.put(GROUPMESSAGE_REQUEST, GroupMessageRequestPacket.class);
		packetTypeMap.put(GROUPMESSAGE_RESPONSE, GroupMessageResponsePacket.class);
		packetTypeMap.put(Command.HEARTBEAT_REQUEST, HeartBeatRequestPacket.class);
		packetTypeMap.put(Command.HEARTBEAT_RESPONSE, HeartBeatResponsePacket.class);

		serializerMap = new HashMap<>();
		Serializer serializer = new JSONSerializer();
		serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
	}

	public ByteBuf encode(ByteBuf byteBuf, Packet packet) {
		// 1. 序列化 java 对象
		byte[] bytes = Serializer.DEFAULT.serialize(packet);

		// 2. 实际编码过程
		byteBuf.writeInt(MAGIC_NUMBER);
		byteBuf.writeByte(packet.getVersion());
		byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
		byteBuf.writeByte(packet.getCommand());
		byteBuf.writeInt(bytes.length);
		byteBuf.writeBytes(bytes);

		return byteBuf;
	}

	public Packet decode(ByteBuf byteBuf) {
		// 跳过 magic number
		byteBuf.skipBytes(4);

		// 跳过版本号
		byteBuf.skipBytes(1);

		// 序列化算法
		byte serializeAlgorithm = byteBuf.readByte();

		// 指令
		byte command = byteBuf.readByte();

		// 数据包长度
		int length = byteBuf.readInt();

		byte[] bytes = new byte[length];
		byteBuf.readBytes(bytes);

		Class<? extends Packet> requestType = getRequestType(command);
		Serializer serializer = getSerializer(serializeAlgorithm);

		if (requestType != null && serializer != null) {
			return serializer.deserialize(requestType, bytes);
		}

		return null;
	}

	private Serializer getSerializer(byte serializeAlgorithm) {

		return serializerMap.get(serializeAlgorithm);
	}

	private Class<? extends Packet> getRequestType(byte command) {

		return packetTypeMap.get(command);
	}
}