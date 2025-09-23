package com.gomo.app.quest.application.port.dto;

import java.util.UUID;

public record CreateAssignQuestDto(UUID id) {

	public static CreateAssignQuestDto of(UUID id) {
		return new CreateAssignQuestDto(id);
	}
}
