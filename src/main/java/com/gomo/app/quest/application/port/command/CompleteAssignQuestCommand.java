package com.gomo.app.quest.application.port.command;

import java.util.UUID;

public record CompleteAssignQuestCommand(UUID participantId, UUID assignQuestId, String proof) {

	public static CompleteAssignQuestCommand of(UUID participantId, UUID assignQuestId, String proof) {
		return new CompleteAssignQuestCommand(participantId, assignQuestId, proof);
	}
}
