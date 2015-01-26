package com.afmobi.cassandra.apache.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class Util {
	
	public static ByteBuffer toByteBuffer(String value)
			throws UnsupportedEncodingException {
		return ByteBuffer.wrap(value.getBytes("utf-8"));
	}

	public static String toString(ByteBuffer buffer)
			throws UnsupportedEncodingException {
		byte[] bytes = new byte[buffer.remaining()];
		buffer.get(bytes);
		return new String(bytes, "utf-8");
	}

}
