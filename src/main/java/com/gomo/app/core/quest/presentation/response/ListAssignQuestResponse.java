package com.gomo.app.core.quest.presentation.response;

import java.util.List;

import com.gomo.app.core.quest.application.port.dto.ListAssignQuestDto;

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

	public static ListAssignQuestResponse from(ListAssignQuestDto dto) {
		return new ListAssignQuestResponse(
			dto.dailyQuests().stream().map(ReadAssignQuestResponse::from).toList(),
			dto.weeklyQuests().stream().map(ReadAssignQuestResponse::from).toList(),
			dto.monthlyQuests().stream().map(ReadAssignQuestResponse::from).toList()
		);
	}
}
