package com.gomo.app.quest.application;

import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.logging.AuditLog;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.domain.service.AssignQuestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class DeleteAssignQuestUseCase {

	private final AssignQuestService assignQuestService;
	private final AssignQuestRepository assignQuestRepository;

	@AuditLog(action = "DELETE_ASSIGN_QUEST")
	public void delete(UUID participantId, UUID assignQuestId) {
		AssignQuest assignQuest = assignQuestService.find(AssignQuestId.of(assignQuestId));
		assignQuest.validateAuthority(participantId);
		assignQuest.ensureNotConfirmed();
		assignQuest.ensureNotCompleted();
		assignQuestRepository.delete(assignQuest);
	}
}
