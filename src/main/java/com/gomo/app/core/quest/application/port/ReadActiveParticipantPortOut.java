package com.gomo.app.core.quest.application.port;

import java.time.LocalDate;
import java.util.List;

import com.gomo.app.core.quest.application.port.dto.ActiveParticipantDto;

public interface ReadActiveParticipantPortOut {

	/**
	 * Retrieves all participants who have logged in on or after the specified date.
	 *
	 * @param lastLoginDate The reference date for determining activity.
	 * @return A list of {@link ActiveParticipantDto}s. Returns an empty list if no
	 *         participants match the criteria; this method does not return null.
	 */
	List<ActiveParticipantDto> findAll(LocalDate lastLoginDate);
}
