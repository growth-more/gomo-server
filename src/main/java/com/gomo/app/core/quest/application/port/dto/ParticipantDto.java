package com.gomo.app.core.quest.application.port.dto;

import java.util.UUID;

public record ParticipantDto(UUID id, int dailyQuota, int weeklyQuota, int monthlyQuota) {

	public static ParticipantDto of(UUID id, int dailyQuota, int weeklyQuota, int monthlyQuota) {
		return new ParticipantDto(id, dailyQuota, weeklyQuota, monthlyQuota);
	}
}
