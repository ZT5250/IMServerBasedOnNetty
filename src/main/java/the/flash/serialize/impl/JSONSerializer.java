package the.flash.serialize.impl;

import com.alibaba.fastjson.JSONObject;

import the.flash.serialize.Serializer;
import the.flash.serialize.SerializerAlgorithm;

public class JSONSerializer implements Serializer{

	@Override
	public byte getSerializerAlgorithm() {
		return SerializerAlgorithm.JSON;
	}

	@Override
	public byte[] serialize(Object object) {
		return JSONObject.toJSONBytes(object);
	}

	@Override
	public <T> T deserialize(Class<T> clazz, byte[] bytes) {
		return JSONObject.parseObject(bytes, clazz);
	}

}
