package com.gomo.app.core.member.application.port;

import java.time.LocalDate;
import java.util.List;

import com.gomo.app.core.member.application.port.dto.ActiveMemberDto;

public interface ReadActiveMemberPortIn {

	/**
	 * Retrieves a list of members who are considered active and have logged in on or after the specified date.
	 *
	 * @param loginCutoffDate The reference date. The query will find members who have logged in
	 *                      on or after this date.
	 * @return A list of {@link ActiveMemberDto}s. Returns an empty list if no active
	 *         members match the criteria; this method does not return null.
	 */
	List<ActiveMemberDto> findAll(LocalDate loginCutoffDate);
}
