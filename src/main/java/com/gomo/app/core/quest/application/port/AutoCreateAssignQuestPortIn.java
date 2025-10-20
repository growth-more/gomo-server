package com.gomo.app.core.quest.application.port;

import java.util.List;

import com.gomo.app.core.quest.application.port.dto.ParticipantDto;
import com.gomo.app.core.quest.domain.model.quest.QuestType;

public interface AutoCreateAssignQuestPortIn {

	/**
	 * Creates assign quests for a given list of participants based on a specified routine type.
	 *
	 * @param participantDtos The list of participants to whom quests will be generated.
	 * @param questType       The type of quest routine to execute (e.g., "DAILY", "WEEKLY" {@link QuestType}).
	 */
	void execute(List<ParticipantDto> participantDtos, String questType);
}
