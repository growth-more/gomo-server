package com.gomo.app.core.quest.presentation.response;

import java.util.List;

import com.gomo.app.core.quest.application.port.dto.ListRepeatQuestDto;

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

	public static ListRepeatQuestResponse from(ListRepeatQuestDto dto) {
		return new ListRepeatQuestResponse(
			dto.dailyQuests().stream().map(ReadRepeatQuestResponse::from).toList(),
			dto.weeklyQuests().stream().map(ReadRepeatQuestResponse::from).toList(),
			dto.monthlyQuests().stream().map(ReadRepeatQuestResponse::from).toList()
		);
	}
}
