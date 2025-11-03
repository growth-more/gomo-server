package com.gomo.app.core.quest.application.port.in;

import java.util.UUID;

public interface RepeatQuestDeleter {

	void delete(UUID participantId, UUID repeatQuestId);
}
