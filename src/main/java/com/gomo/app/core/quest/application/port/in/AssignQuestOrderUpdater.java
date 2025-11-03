package com.gomo.app.core.quest.application.port.in;

import com.gomo.app.core.quest.application.port.command.OrderUpdateAssignQuestCommand;

public interface AssignQuestOrderUpdater {

	void update(OrderUpdateAssignQuestCommand command);
}
