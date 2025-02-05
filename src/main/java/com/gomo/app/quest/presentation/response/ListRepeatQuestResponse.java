package com.gomo.app.quest.presentation.response;

import java.util.List;

import lombok.Getter;

@Getter
public class ListRepeatQuestResponse {

	private List<ReadRepeatQuestResponse> dailyQuests;
	private List<ReadRepeatQuestResponse> weeklyQuests;
	private List<ReadRepeatQuestResponse> monthlyQuests;

	private ListRepeatQuestResponse(
		List<ReadRepeatQuestResponse> dailyQuests,
		List<ReadRepeatQuestResponse> weeklyQuests,
		List<ReadRepeatQuestResponse> monthlyQuests
	) {
		this.dailyQuests = dailyQuests;
		this.weeklyQuests = weeklyQuests;
		this.monthlyQuests = monthlyQuests;
	}

	public static ListRepeatQuestResponse of(
		List<ReadRepeatQuestResponse> dailyQuests,
		List<ReadRepeatQuestResponse> weeklyQuests,
		List<ReadRepeatQuestResponse> monthlyQuests
	) {
		return new ListRepeatQuestResponse(dailyQuests, weeklyQuests, monthlyQuests);
	}
}
