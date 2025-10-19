package com.gomo.app.core.quest.application.port.command;

import java.util.List;
import java.util.UUID;

import com.gomo.app.core.quest.domain.model.quest.QuestType;

/**
 * A command object that encapsulates the data required to request the creation of a quest pool.
 *
 * @param participantId The unique identifier of the participant for whom the quest pool is being created.
 * @param subjects      A list of the participant's subjects, used as a reference for generating quests.
 * @param questType     The type of quests to be generated (e.g., "DAILY", "WEEKLY"); refer {@link QuestType}.
 * @param limit         The maximum capacity of quest pools to generate for the participant.
 */
public record CreateQuestPoolCommand(UUID participantId, List<Subject> subjects, String questType, int limit) {

	public static CreateQuestPoolCommand of(UUID participantId, List<Subject> subjects, String questType, int limit) {
		return new CreateQuestPoolCommand(participantId, subjects, questType, limit);
	}

	public record Subject(UUID id, String name, int level) {

		public static Subject of(UUID id, String name, int level) {
			return new Subject(id, name, level);
		}
	}
}
