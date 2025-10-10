package com.gomo.app.core.streak.application.port.dto;

import java.util.List;

public record ListStreakDto(List<StreakDto> dailyStreaks, List<StreakDto> weeklyStreaks, List<StreakDto> monthlyStreaks) {

	public static ListStreakDto of(List<StreakDto> dailyStreaks, List<StreakDto> weeklyStreaks, List<StreakDto> monthlyStreaks) {
		return new ListStreakDto(dailyStreaks, weeklyStreaks, monthlyStreaks);
	}
}
