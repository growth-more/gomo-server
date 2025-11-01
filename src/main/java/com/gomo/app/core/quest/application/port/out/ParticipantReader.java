package com.gomo.app.core.quest.application.port.out;

import java.util.UUID;

import com.gomo.app.core.quest.application.port.dto.ParticipantDto;

public interface ParticipantReader {

	/**
	 * Retrieves a single participant by id.
	 *
	 * @param participantId The id of the participant to find.
	 * @return A {@link ParticipantDto} with the participant's data.
	 */
	ParticipantDto read(UUID participantId);
}
