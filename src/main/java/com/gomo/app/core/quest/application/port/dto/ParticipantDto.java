package com.gomo.app.core.quest.application.port.dto;

import java.util.UUID;

import com.gomo.app.core.quest.domain.model.participant.Participant;
import com.gomo.app.core.quest.domain.model.participant.QuestQuota;

public record ParticipantDto(UUID id, int dailyQuota, int weeklyQuota, int monthlyQuota) {

	public static ParticipantDto of(UUID id, int dailyQuota, int weeklyQuota, int monthlyQuota) {
		return new ParticipantDto(id, dailyQuota, weeklyQuota, monthlyQuota);
	}

	public Participant toParticipant() {
		return Participant.of(id, QuestQuota.of(dailyQuota, weeklyQuota, monthlyQuota));
	}
}
