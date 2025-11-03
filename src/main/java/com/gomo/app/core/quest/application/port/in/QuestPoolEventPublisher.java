package com.gomo.app.core.quest.application.port.in;

import com.gomo.app.core.quest.application.port.command.PublishCreateQuestPoolCommand;

public interface QuestPoolEventPublisher {

	void publish(PublishCreateQuestPoolCommand command);
}
