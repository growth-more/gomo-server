package com.gomo.app.core.quest.application.usecase;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.quest.application.port.command.UpdateAssignQuestCommand;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.model.quest.QuestContent;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.subject.SubjectName;
import com.gomo.app.core.quest.domain.service.AssignQuestService;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
public class UpdateAssignQuestUseCase {

	private final AssignQuestService assignQuestService;

	@AuditLog(action = "UPDATE_ASSIGN_QUEST")
	public void update(UpdateAssignQuestCommand command) {
		AssignQuest assignQuest = assignQuestService.find(command.assignQuestId());
		assignQuest.validateAuthority(command.participantId());

		QuestType requestedQuestType = QuestType.valueOf(command.questType());
		assignQuest.ensureSameQuestType(requestedQuestType);
		assignQuest.ensureNotConfirmed();
		assignQuest.ensureNotCompleted();

		assignQuest.updateQuest(
			command.subjectId(),
			SubjectName.of(command.subjectName()),
			requestedQuestType,
			QuestContent.of(command.content())
		);
	}
}
