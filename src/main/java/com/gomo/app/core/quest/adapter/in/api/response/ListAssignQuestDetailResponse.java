package com.gomo.app.core.quest.adapter.in.api.response;

import java.util.List;

import com.gomo.app.core.quest.application.port.dto.ListAssignQuestDetailDto;

import lombok.Getter;

@Getter
public class ListAssignQuestDetailResponse {

	private List<ReadAssignQuestDetailResponse> dailyQuests;
	private List<ReadAssignQuestDetailResponse> weeklyQuests;
	private List<ReadAssignQuestDetailResponse> monthlyQuests;

	private ListAssignQuestDetailResponse(
		List<ReadAssignQuestDetailResponse> dailyQuests,
		List<ReadAssignQuestDetailResponse> weeklyQuests,
		List<ReadAssignQuestDetailResponse> monthlyQuests
	) {
		this.dailyQuests = dailyQuests;
		this.weeklyQuests = weeklyQuests;
		this.monthlyQuests = monthlyQuests;
	}

	public static ListAssignQuestDetailResponse from(ListAssignQuestDetailDto dto) {
		return new ListAssignQuestDetailResponse(
			dto.dailyQuests().stream().map(ReadAssignQuestDetailResponse::from).toList(),
			dto.weeklyQuests().stream().map(ReadAssignQuestDetailResponse::from).toList(),
			dto.monthlyQuests().stream().map(ReadAssignQuestDetailResponse::from).toList()
		);
	}
}
