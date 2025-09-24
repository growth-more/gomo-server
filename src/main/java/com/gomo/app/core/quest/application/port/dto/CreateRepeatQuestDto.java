package com.gomo.app.core.quest.application.port.dto;

import java.util.UUID;

public record CreateRepeatQuestDto(UUID id) {

	public static CreateRepeatQuestDto of(UUID id) {
		return new CreateRepeatQuestDto(id);
	}
}
