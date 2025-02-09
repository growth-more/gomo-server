package com.gomo.app.common.util;

import java.util.UUID;

import com.fasterxml.uuid.Generators;

public class TimestampGenerator {

	public static long generate() {
		UUID uuid = Generators.timeBasedEpochRandomGenerator().generate();
		String hex = uuid.toString().replace("-", "").substring(0, 16);
		return Long.parseUnsignedLong(hex, 16);
	}
}
