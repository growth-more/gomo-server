package com.gomo.app.quest.presentation.response;

import java.util.List;

import lombok.Getter;

@Getter
public class ListAssignQuestResponse {

	private List<ReadAssignQuestResponse> dailyQuests;
	private List<ReadAssignQuestResponse> weeklyQuests;
	private List<ReadAssignQuestResponse> monthlyQuests;

	private ListAssignQuestResponse(
		List<ReadAssignQuestResponse> dailyQuests,
		List<ReadAssignQuestResponse> weeklyQuests,
		List<ReadAssignQuestResponse> monthlyQuests
	) {
		this.dailyQuests = dailyQuests;
		this.weeklyQuests = weeklyQuests;
		this.monthlyQuests = monthlyQuests;
	}

	public static ListAssignQuestResponse of(
		List<ReadAssignQuestResponse> dailyQuests,
		List<ReadAssignQuestResponse> weeklyQuests,
		List<ReadAssignQuestResponse> monthlyQuests
	) {
		return new ListAssignQuestResponse(dailyQuests, weeklyQuests, monthlyQuests);
	}
}
