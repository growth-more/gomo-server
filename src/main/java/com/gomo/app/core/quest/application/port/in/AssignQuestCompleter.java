package com.gomo.app.core.quest.application.port.in;

import com.gomo.app.core.quest.application.port.command.CompleteAssignQuestCommand;

public interface AssignQuestCompleter {

	void complete(CompleteAssignQuestCommand command);
}
