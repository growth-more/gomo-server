package com.gomo.app.quest.presentation.response;

import java.util.List;

import lombok.Getter;

@Getter
public class HistoryListAssignQuestResponse {

	private List<HistoryReadAssignQuestResponse> dailyHistoryQuests;
	private List<HistoryReadAssignQuestResponse> weeklyHistoryQuests;
	private List<HistoryReadAssignQuestResponse> monthlyHistoryQuests;

	private HistoryListAssignQuestResponse(
		List<HistoryReadAssignQuestResponse> dailyHistoryQuests,
		List<HistoryReadAssignQuestResponse> weeklyHistoryQuests,
		List<HistoryReadAssignQuestResponse> monthlyHistoryQuests
	) {
		this.dailyHistoryQuests = dailyHistoryQuests;
		this.weeklyHistoryQuests = weeklyHistoryQuests;
		this.monthlyHistoryQuests = monthlyHistoryQuests;
	}

	public static HistoryListAssignQuestResponse of(
		List<HistoryReadAssignQuestResponse> dailyHistoryQuests,
		List<HistoryReadAssignQuestResponse> weeklyHistoryQuests,
		List<HistoryReadAssignQuestResponse> monthlyHistoryQuests
	) {
		return new HistoryListAssignQuestResponse(dailyHistoryQuests, weeklyHistoryQuests, monthlyHistoryQuests);
	}
}
