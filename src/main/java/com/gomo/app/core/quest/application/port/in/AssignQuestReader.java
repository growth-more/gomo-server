package com.gomo.app.core.quest.application.port.in;

import java.util.List;

import com.gomo.app.core.quest.application.port.command.ListAssignQuestCommand;
import com.gomo.app.core.quest.application.port.dto.AssignQuestDto;

public interface AssignQuestReader {

	List<AssignQuestDto> readAll(ListAssignQuestCommand command);
}
