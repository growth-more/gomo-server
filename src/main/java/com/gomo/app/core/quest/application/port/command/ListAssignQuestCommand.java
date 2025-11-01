package com.gomo.app.core.quest.application.port.command;

import java.time.LocalDateTime;
import java.util.UUID;

public record ListAssignQuestCommand(UUID participantId, boolean isCompleted, LocalDateTime startDate, LocalDateTime endDate) {

	public static ListAssignQuestCommand of(UUID participantId, boolean isCompleted, LocalDateTime startDate, LocalDateTime endDate) {
		return new ListAssignQuestCommand(participantId, isCompleted, startDate, endDate);
	}
}
