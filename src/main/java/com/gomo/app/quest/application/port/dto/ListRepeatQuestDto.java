package com.gomo.app.quest.application.port.dto;

import java.util.List;

public record ListRepeatQuestDto(List<RepeatQuestDto> dailyQuests, List<RepeatQuestDto> weeklyQuests, List<RepeatQuestDto> monthlyQuests) {

	public static ListRepeatQuestDto of(List<RepeatQuestDto> dailyQuests, List<RepeatQuestDto> weeklyQuests, List<RepeatQuestDto> monthlyQuests) {
		return new ListRepeatQuestDto(dailyQuests, weeklyQuests, monthlyQuests);
	}
}
