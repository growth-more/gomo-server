package com.gomo.app.core.member.application.port.in;

import com.gomo.app.core.member.application.port.command.UpdateQuestPropertyCommand;
import com.gomo.app.core.member.domain.exception.MemberNotFoundException;

public interface QuestPropertyUpdater {

	/**
	 * Updates the quest-related properties for a specific member.
	 *
	 * @param command A {@link UpdateQuestPropertyCommand} containing the member's ID and the new quest property values.
	 * @throws MemberNotFoundException if no member is found with the ID provided in the command.
	 */
	void update(UpdateQuestPropertyCommand command);
}
