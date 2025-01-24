package com.gomo.app.quest.presentation.response;

import java.util.List;

import lombok.Getter;

@Getter
public class ListAssignQuestResponse {

	private List<ReadAssignQuestResponse> assignQuests;

	private ListAssignQuestResponse(List<ReadAssignQuestResponse> assignQuests) {
		this.assignQuests = assignQuests;
	}

	public static ListAssignQuestResponse of(List<ReadAssignQuestResponse> assignQuests) {
		return new ListAssignQuestResponse(assignQuests);
	}
}
