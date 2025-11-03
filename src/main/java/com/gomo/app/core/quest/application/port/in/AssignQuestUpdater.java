package com.gomo.app.core.quest.application.port.in;

import com.gomo.app.core.quest.application.port.command.UpdateAssignQuestCommand;

public interface AssignQuestUpdater {

	void update(UpdateAssignQuestCommand command);
}
