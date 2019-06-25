package the.flash.attribute;

import io.netty.util.AttributeKey;
import the.flash.dto.Session;

public interface Attributes {
	AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
	AttributeKey<Session> SESSION = AttributeKey.newInstance("SESSION");
}
