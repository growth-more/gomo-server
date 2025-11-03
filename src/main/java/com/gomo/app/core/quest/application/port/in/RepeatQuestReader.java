package com.gomo.app.core.quest.application.port.in;

import java.util.UUID;

import com.gomo.app.core.quest.application.port.dto.ListRepeatQuestDto;

public interface RepeatQuestReader {

	ListRepeatQuestDto readAll(UUID participantId);
}
