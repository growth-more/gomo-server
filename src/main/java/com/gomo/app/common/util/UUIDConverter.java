package com.gomo.app.common.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDConverter {
	public static byte[] uuidToBytes(UUID uuid) {
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());
		return bb.array();
	}

	public static UUID bytesToUuid(byte[] bytes) {
		ByteBuffer bb = ByteBuffer.wrap(bytes);
		long high = bb.getLong();
		long low = bb.getLong();
		return new UUID(high, low);
	}
}
