package com.gomo.app.core.member.application.port;

import java.util.UUID;

import com.gomo.app.core.member.application.port.dto.MemberDto;
import com.gomo.app.core.member.exception.MemberNotFoundException;

public interface ReadMemberPortIn {

	/**
	 * Retrieves a specific member by id.
	 *
	 * @param id The id (UUID) of the member to retrieve.
	 * @return A {@link MemberDto} containing the member's information.
	 * @throws MemberNotFoundException if no member with the specified ID can be found.
	 */
	MemberDto find(UUID id);
}
