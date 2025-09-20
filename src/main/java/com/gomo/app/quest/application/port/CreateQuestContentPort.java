package com.gomo.app.quest.application.port;

import java.util.List;

import com.gomo.app.quest.application.port.command.CreateQuestContentCommand;
import com.gomo.app.quest.application.port.dto.QuestContentDto;

public interface CreateQuestContentPort {

	List<QuestContentDto> create(CreateQuestContentCommand command);
}
