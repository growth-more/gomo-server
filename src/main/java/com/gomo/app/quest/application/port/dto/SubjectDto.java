package com.gomo.app.quest.application.port.dto;

import java.util.UUID;

public record SubjectDto(UUID subjectId, UUID participantId, String subjectName, int level) {

	public static SubjectDto of(UUID subjectId, UUID participantId, String subjectName, int level) {
		return new SubjectDto(subjectId, participantId, subjectName, level);
	}
}
