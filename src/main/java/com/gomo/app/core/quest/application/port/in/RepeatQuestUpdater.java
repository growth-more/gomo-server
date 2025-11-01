package com.gomo.app.core.quest.application.port.in;

import com.gomo.app.core.quest.application.port.command.UpdateRepeatQuestCommand;

public interface RepeatQuestUpdater {

	void update(UpdateRepeatQuestCommand command);
}
