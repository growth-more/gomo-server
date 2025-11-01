package com.gomo.app.core.quest.application.port.in;

import java.util.UUID;

import com.gomo.app.core.quest.application.port.dto.AssignQuestDetailDto;

public interface AssignQuestReRoller {

	AssignQuestDetailDto reRoll(UUID participantId, UUID assignQuestId);
}
