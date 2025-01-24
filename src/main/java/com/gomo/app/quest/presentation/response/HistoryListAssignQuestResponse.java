package com.gomo.app.quest.presentation.response;

import java.util.List;

import lombok.Getter;

@Getter
public class HistoryListAssignQuestResponse {

	private List<HistoryReadAssignQuestResponse> histories;

	private HistoryListAssignQuestResponse(List<HistoryReadAssignQuestResponse> histories) {
		this.histories = histories;
	}

	public static HistoryListAssignQuestResponse of(List<HistoryReadAssignQuestResponse> histories) {
		return new HistoryListAssignQuestResponse(histories);
	}
}
