package com.gomo.app.core.quest.application.port;

import com.gomo.app.core.quest.application.port.command.PublishCreateQuestPoolCommand;

public interface PublishCreateQuestPoolPortIn {

	void publish(PublishCreateQuestPoolCommand command);
}
