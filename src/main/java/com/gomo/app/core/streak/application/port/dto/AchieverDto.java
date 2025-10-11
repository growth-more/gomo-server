package com.gomo.app.core.streak.application.port.dto;

import java.util.UUID;

import com.gomo.app.core.streak.domain.model.Achiever;

public record AchieverDto(UUID id, int longestStreakDays, int currentStreakDays) {

	public static AchieverDto from(Achiever achiever) {
		return new AchieverDto(achiever.getId(), achiever.getLongestStreakDays(), achiever.getCurrentStreakDays());
	}
}
