package com.gomo.app.core.quest.application.port.in;

import com.gomo.app.core.quest.application.port.command.OrderUpdateRepeatQuestCommand;

public interface RepeatQuestOrderUpdater {

	void update(OrderUpdateRepeatQuestCommand command);
}
