package com.gomo.app.quest.presentation.response;

import java.util.UUID;

import com.gomo.app.quest.domain.model.AssignQuestId;

import lombok.Getter;

@Getter
public class CreateAssignQuestResponse {

	private UUID id;

	private CreateAssignQuestResponse(UUID id) {
		this.id = id;
	}

	public static CreateAssignQuestResponse of(AssignQuestId id) {
		return new CreateAssignQuestResponse(id.getId());
	}
}
