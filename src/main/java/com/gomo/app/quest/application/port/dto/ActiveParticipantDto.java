package com.gomo.app.quest.application.port.dto;

import java.util.UUID;

public record ActiveParticipantDto(UUID participantId, int dailyQuota, int weeklyQuota, int monthlyQuota) {

	public static ActiveParticipantDto of(UUID participantId, int dailyQuota, int weeklyQuota, int monthlyQuota) {
		return new ActiveParticipantDto(participantId, dailyQuota, weeklyQuota, monthlyQuota);
	}
}
