package com.gomo.app.core.quest.application.port;

import java.time.LocalDate;

import com.gomo.app.core.quest.domain.model.quest.QuestType;

public interface FillQuestPoolPortIn {

	/**
	 * Finds all active participants and generates a specified number of new quests for each of them.
	 *
	 * @param lastLoginDateOfTargets The reference date to identify active participants (i.e., those
	 *                               who have logged in on or after this date).
	 * @param questType              The specific type of quests to be generated (e.g., DAILY, WEEKLY).
	 * @param limit                  The maximum number of quests to generate per participant.
	 */
	void fillForActiveParticipants(LocalDate lastLoginDateOfTargets, QuestType questType, int limit);
}
