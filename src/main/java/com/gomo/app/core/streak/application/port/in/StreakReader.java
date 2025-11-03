package com.gomo.app.core.streak.application.port.in;

import java.time.LocalDate;
import java.util.UUID;

import com.gomo.app.core.streak.application.port.dto.ListStreakDto;

public interface StreakReader {

	/**
	 * Retrieves all streaks for a specific achiever within a given date range.
	 * The results are categorized by streak type (e.g., daily, weekly, monthly).
	 *
	 * @param achieverId The ID of the achiever whose streaks are to be retrieved.
	 * @param startDate  The start date of the period (inclusive).
	 * @param endDate    The end date of the period (inclusive).
	 * @return A {@link ListStreakDto} containing categorized lists of streaks.
	 *         The lists will be empty if no streaks are found for the given period; this method does not return null.
	 */
	ListStreakDto findAll(UUID achieverId, LocalDate startDate, LocalDate endDate);
}
