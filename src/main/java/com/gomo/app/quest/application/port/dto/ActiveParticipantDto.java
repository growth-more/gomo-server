package com.gomo.app.quest.application.port.dto;

import java.util.UUID;

public record ActiveParticipantDto(UUID participantId) {

	public static ActiveParticipantDto of(UUID participantId) {
		return new ActiveParticipantDto(participantId);
	}
}
