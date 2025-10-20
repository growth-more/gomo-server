package com.gomo.app.core.quest.application.port.dto;

import java.util.UUID;

public record SubjectDto(UUID id, UUID participantId, String name, int level) {

	public static SubjectDto of(UUID id, UUID participantId, String name, int level) {
		return new SubjectDto(id, participantId, name, level);
	}
}
