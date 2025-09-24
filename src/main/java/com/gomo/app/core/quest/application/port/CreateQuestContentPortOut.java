package com.gomo.app.core.quest.application.port;

import java.util.List;

import com.gomo.app.core.quest.application.port.command.CreateQuestContentCommand;
import com.gomo.app.core.quest.application.port.dto.QuestContentDto;

public interface CreateQuestContentPortOut {

	List<QuestContentDto> create(CreateQuestContentCommand command);
}
