package com.gomo.app.core.quest.application.port.command;

import java.time.LocalDateTime;
import java.util.UUID;

public record CompleteAssignQuestCommand(UUID participantId, UUID assignQuestId, String proof, LocalDateTime completedDateTime) {

	public static CompleteAssignQuestCommand of(UUID participantId, UUID assignQuestId, String proof, LocalDateTime completedDateTime) {
		return new CompleteAssignQuestCommand(participantId, assignQuestId, proof, completedDateTime);
	}
}
