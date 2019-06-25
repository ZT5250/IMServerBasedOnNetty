package the.flash.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;
import the.flash.attribute.Attributes;
import the.flash.dto.Session;

public class ClientSessionUtil {
	private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

	public static void bindSession(Session session, Channel channel) {
		userIdChannelMap.put(session.getUserId(), channel);
		channel.attr(Attributes.SESSION).set(session);
	}

	public static void unbindSession(Channel channel) {
		if (hasLogin(channel)) {
			userIdChannelMap.remove(getSession(channel).getUserId());
			channel.attr(Attributes.SESSION).set(null);
		}
	}

	public static boolean hasLogin(Channel channel) {
		return channel.hasAttr(Attributes.SESSION) && channel.attr(Attributes.SESSION).get() != null;
	}

	public static Session getSession(Channel channel) {
		return channel.attr(Attributes.SESSION).get();
	}
	public static Channel getChannel(String userId) {
		return userIdChannelMap.get(userId);
	}
}
