package com.gomo.app.core.quest.application.port.out;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.gomo.app.core.quest.application.port.dto.SubjectDto;

public interface SubjectReader {

	/**
	 * Retrieves all subjects related to a specific participant.
	 *
	 * @param participantId The id of the participant whose subjects are to be retrieved.
	 * @return A list of {@link SubjectDto}s. Returns an empty list if the participant
	 *         has no associated subjects; this method does not return null.
	 */
	List<SubjectDto> readAll(UUID participantId);

	/**
	 * Retrieves all subjects related to participants.
	 *
	 * @param participantIds The ids of participants whose subjects are to be retrieved.
	 * @return A list of {@link SubjectDto}s. Returns an empty list if participants
	 *         has no associated subjects; this method does not return null.
	 */
	List<SubjectDto> readAllByParticipantIds(Set<UUID> participantIds);
}
