package com.gomo.app.core.quest.application.port.dto;

import java.util.List;

public record ListAssignQuestDto(List<AssignQuestDto> dailyQuests, List<AssignQuestDto> weeklyQuests, List<AssignQuestDto> monthlyQuests) {

	public static ListAssignQuestDto of(List<AssignQuestDto> dailyQuests, List<AssignQuestDto> weeklyQuests, List<AssignQuestDto> monthlyQuests) {
		return new ListAssignQuestDto(dailyQuests, weeklyQuests, monthlyQuests);
	}
}
