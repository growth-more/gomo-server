package com.gomo.app.core.quest.presentation.api.request;

import java.util.UUID;

import lombok.Getter;

@Getter
public class ReRollAssignQuestRequest {

	private UUID assignQuestId;

	private ReRollAssignQuestRequest(UUID assignQuestId) {
		this.assignQuestId = assignQuestId;
	}

	public static ReRollAssignQuestRequest of(UUID assignQuestId) {
		return new ReRollAssignQuestRequest(assignQuestId);
	}
}
