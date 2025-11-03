package com.gomo.app.core.quest.application.port.in;

import java.util.UUID;

import com.gomo.app.core.quest.application.port.command.CreateRepeatQuestCommand;

public interface RepeatQuestCreator {

	UUID create(CreateRepeatQuestCommand command);
}
