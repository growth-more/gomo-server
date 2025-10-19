package com.gomo.app.core.quest.application.port.command;

import java.util.List;

import com.gomo.app.core.quest.domain.model.participant.Participant;

public record PublishCreateQuestPoolCommand(List<Participant> participants, String questType, Long limitPerParticipant) {

	public static PublishCreateQuestPoolCommand of(List<Participant> participants, String questType, Long limitPerParticipant) {
		return new PublishCreateQuestPoolCommand(participants, questType, limitPerParticipant);
	}
}
