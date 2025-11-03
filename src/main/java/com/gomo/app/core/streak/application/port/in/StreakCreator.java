package com.gomo.app.core.streak.application.port.in;

import java.time.LocalDate;
import java.util.UUID;

import com.gomo.app.core.streak.domain.model.StreakType;

public interface StreakCreator {

	/**
	 * Creates a streak record for achiever on a specific date.
	 *
	 * @param achieverId   The id of the achiever who is filling the streak.
	 * @param streakType   The type of the streak. Must match a name from the {@link StreakType} enum (e.g., "DAILY").
	 * @param creationDate The date for which the streak is being recorded.
	 */
	void create(UUID achieverId, String streakType, LocalDate creationDate);
}
