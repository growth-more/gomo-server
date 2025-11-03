package com.gomo.app.core.quest.application.port.in;

import java.util.UUID;

public interface AssignQuestConfirmer {

	void confirm(UUID accessorId, UUID assignQuestId);
}
