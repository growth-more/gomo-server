package com.gomo.app.quest.application;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.model.QuestContent;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.SubjectId;
import com.gomo.app.quest.domain.model.SubjectName;
import com.gomo.app.quest.domain.service.AssignQuestService;
import com.gomo.app.quest.presentation.request.UpdateAssignQuestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateAssignQuestUseCase {

	private final AssignQuestService assignQuestService;

	public void update(UUID accessorId, AssignQuestId assignQuestId, UpdateAssignQuestRequest request) {
		AssignQuest assignQuest = assignQuestService.find(assignQuestId);
		assignQuest.validateAuthority(accessorId);

		QuestType requestedQuestType = request.getQuestType();
		assignQuest.ensureSameQuestType(requestedQuestType);
		assignQuest.ensureNotConfirmed();
		assignQuest.ensureNotCompleted();

		assignQuest.updateQuest(
			SubjectId.of(request.getSubjectId()),
			SubjectName.of(request.getSubjectName()),
			requestedQuestType,
			QuestContent.of(request.getContent())
		);
	}
}
