package com.gomo.app.core.quest.application.port.command;

import java.time.LocalDateTime;
import java.util.UUID;

public record CalendarAssignQuestCommand(UUID participantId, boolean isCompleted, LocalDateTime startDate, LocalDateTime endDate) {

	public static CalendarAssignQuestCommand of(UUID participantId, boolean isCompleted, LocalDateTime startDate, LocalDateTime endDate) {
		return new CalendarAssignQuestCommand(participantId, isCompleted, startDate, endDate);
	}
}
