package com.gomo.app.quest.application;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.logging.AuditLog;
import com.gomo.app.quest.application.port.command.UpdateAssignQuestCommand;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.model.QuestContent;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.SubjectId;
import com.gomo.app.quest.domain.model.SubjectName;
import com.gomo.app.quest.domain.service.AssignQuestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateAssignQuestUseCase {

	private final AssignQuestService assignQuestService;

	@AuditLog(action = "UPDATE_ASSIGN_QUEST")
	public void update(UpdateAssignQuestCommand command) {
		AssignQuest assignQuest = assignQuestService.find(AssignQuestId.of(command.assignQuestId()));
		assignQuest.validateAuthority(command.participantId());

		QuestType requestedQuestType = QuestType.valueOf(command.questType());
		assignQuest.ensureSameQuestType(requestedQuestType);
		assignQuest.ensureNotConfirmed();
		assignQuest.ensureNotCompleted();

		assignQuest.updateQuest(
			SubjectId.of(command.subjectId()),
			SubjectName.of(command.subjectName()),
			requestedQuestType,
			QuestContent.of(command.content())
		);
	}
}
