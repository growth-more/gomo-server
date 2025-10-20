package com.gomo.app.core.quest.application.usecase;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.core.quest.domain.service.AssignQuestService;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
public class DeleteAssignQuestUseCase {

	private final AssignQuestService assignQuestService;
	private final AssignQuestRepository assignQuestRepository;

	@AuditLog(action = "DELETE_ASSIGN_QUEST")
	public void delete(UUID participantId, UUID assignQuestId) {
		AssignQuest assignQuest = assignQuestService.find(assignQuestId);
		assignQuest.validateAuthority(participantId);
		assignQuest.ensureNotConfirmed();
		assignQuest.ensureNotCompleted();
		assignQuestRepository.delete(assignQuest);
	}
}
