package com.gomo.app.common.util;

import java.util.UUID;

import com.fasterxml.uuid.Generators;

public class TimestampGenerator {

	public static long generate() {
		UUID uuid = Generators.timeBasedEpochRandomGenerator().generate();
		String[] parts = uuid.toString().split("-");
		String highBitsHex = parts[0] + parts[1].substring(0, 4);
		return Long.parseLong(highBitsHex, 16);
	}
}
