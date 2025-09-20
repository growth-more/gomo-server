package com.gomo.app.quest.application.port.command;

import java.util.UUID;

public record ReadSubjectCommand(UUID participantId) {

	public static ReadSubjectCommand of(UUID participantId) {
		return new ReadSubjectCommand(participantId);
	}
}
