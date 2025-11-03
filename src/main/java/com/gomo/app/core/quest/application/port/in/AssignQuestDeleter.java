package com.gomo.app.core.quest.application.port.in;

import java.util.UUID;

public interface AssignQuestDeleter {

	void delete(UUID participantId, UUID assignQuestId);
}
