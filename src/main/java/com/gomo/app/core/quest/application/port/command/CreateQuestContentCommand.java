package com.gomo.app.core.quest.application.port.command;

import java.util.List;
import java.util.UUID;

import com.gomo.app.core.quest.domain.model.quest.QuestType;

/**
 * @param questType The quest type (e.g., "DAILY", "WEEKLY"); refer {@link QuestType}.
 */
public record CreateQuestContentCommand(UUID participantId, List<Subject> subjects, String questType, int count) {

	public static CreateQuestContentCommand of(UUID participantId, List<Subject> subjects, String questType, int count) {
		return new CreateQuestContentCommand(participantId, subjects, questType, count);
	}

	public record Subject(UUID id, String name, int level) {

		public static Subject of(UUID id, String name, int level) {
			return new Subject(id, name, level);
		}
	}
}
