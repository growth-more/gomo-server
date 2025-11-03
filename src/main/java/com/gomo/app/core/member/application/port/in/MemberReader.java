package com.gomo.app.core.member.application.port.in;

import java.util.UUID;

import com.gomo.app.core.member.application.port.dto.MemberDto;
import com.gomo.app.core.member.domain.exception.MemberNotFoundException;

public interface MemberReader {

	/**
	 * Retrieves a specific member by id.
	 *
	 * @param id The id (UUID) of the member to retrieve.
	 * @return A {@link MemberDto} containing the member's information.
	 * @throws MemberNotFoundException if no member with the specified ID can be found.
	 */
	MemberDto read(UUID id);
}
