package com.gomo.app.core.quest.presentation.api.response;

import java.util.UUID;

import lombok.Getter;

@Getter
public class CreateAssignQuestResponse {

	private UUID id;

	private CreateAssignQuestResponse(UUID id) {
		this.id = id;
	}

	public static CreateAssignQuestResponse of(UUID id) {
		return new CreateAssignQuestResponse(id);
	}
}
