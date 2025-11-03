package com.gomo.app.core.quest.application.port.in;

import java.util.UUID;

import com.gomo.app.core.quest.application.port.command.CreateAssignQuestCommand;

public interface AssignQuestCreator {

	UUID create(CreateAssignQuestCommand command);
}
