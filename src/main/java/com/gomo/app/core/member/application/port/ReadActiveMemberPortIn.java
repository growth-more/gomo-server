package com.gomo.app.core.member.application.port;

import java.time.LocalDate;
import java.util.List;

import com.gomo.app.core.member.application.port.dto.ActiveMemberDto;

public interface ReadActiveMemberPortIn {

	/**
	 * Retrieves members with active status who logged in since the given date.
	 *
	 * @param lastLoginDate reference date for last login
	 * @return list of active members
	 */
	List<ActiveMemberDto> findAll(LocalDate lastLoginDate);
}
