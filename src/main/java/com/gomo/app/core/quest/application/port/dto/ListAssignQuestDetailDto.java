package com.gomo.app.core.quest.application.port.dto;

import java.util.List;

public record ListAssignQuestDetailDto(List<AssignQuestDetailDto> dailyQuests, List<AssignQuestDetailDto> weeklyQuests, List<AssignQuestDetailDto> monthlyQuests) {

	public static ListAssignQuestDetailDto of(List<AssignQuestDetailDto> dailyQuests, List<AssignQuestDetailDto> weeklyQuests, List<AssignQuestDetailDto> monthlyQuests) {
		return new ListAssignQuestDetailDto(dailyQuests, weeklyQuests, monthlyQuests);
	}
}
