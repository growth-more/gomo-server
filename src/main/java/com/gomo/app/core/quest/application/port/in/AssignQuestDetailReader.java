package com.gomo.app.core.quest.application.port.in;

import java.util.UUID;

import com.gomo.app.core.quest.application.port.dto.ListAssignQuestDetailDto;

public interface AssignQuestDetailReader {

	ListAssignQuestDetailDto readAll(UUID participantId);
}
