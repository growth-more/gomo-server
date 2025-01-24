package com.gomo.app.streak.common.fixture;

import java.util.UUID;

public class StreakFixture {

	private static String STREAK_1_ID = "203ece27-d868-11ef-824e-338baee5b682";
	private static String STREAK_2_ID = "febad463-d868-11ef-826f-395aa04e34d3";

	public static UUID streak1Id() {
		return UUID.fromString(STREAK_1_ID);
	}

	public static UUID streak2Id() {
		return UUID.fromString(STREAK_2_ID);
	}
}
