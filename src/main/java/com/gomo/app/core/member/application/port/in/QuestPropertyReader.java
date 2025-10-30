package com.gomo.app.core.member.application.port.in;

import java.util.UUID;

import com.gomo.app.core.member.application.port.dto.QuestPropertyDto;
import com.gomo.app.core.member.domain.exception.MemberNotFoundException;

public interface QuestPropertyReader {

	/**
	 * Retrieves the quest-related properties for a specific member.
	 *
	 * @param memberId The ID of the member whose quest properties are to be retrieved.
	 * @return A {@link QuestPropertyDto} containing the member's quest properties.
	 * @throws MemberNotFoundException if no member is found with the provided ID.
	 */
	QuestPropertyDto read(UUID memberId);
}
