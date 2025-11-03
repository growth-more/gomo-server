package com.gomo.app.core.quest.application.port.out;

import java.util.List;

import com.gomo.app.core.quest.application.port.command.CreateQuestContentCommand;
import com.gomo.app.core.quest.application.port.dto.QuestContentDto;

public interface QuestContentCreator {

	/**
	 * Creates new quest content based on the provided command.
	 *
	 * @param command A command object containing all necessary parameters for content creation.
	 * @return A list of {@link QuestContentDto}s representing the newly created quest content.
	 */
	List<QuestContentDto> create(CreateQuestContentCommand command);
}
