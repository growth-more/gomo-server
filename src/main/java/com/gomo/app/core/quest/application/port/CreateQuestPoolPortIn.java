package com.gomo.app.core.quest.application.port;

import com.gomo.app.core.quest.application.port.command.CreateQuestPoolCommand;

public interface CreateQuestPoolPortIn {

	/**
	 * Creates a quest pool for a specific participant based on the provided command.
	 *
	 * @param command A command object containing the necessary data for quest pool creation,
	 *                such as participant ID, subjects, quest type, and limit.
	 */
	void create(CreateQuestPoolCommand command);
}
