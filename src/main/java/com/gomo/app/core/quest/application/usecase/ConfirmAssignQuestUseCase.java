package com.gomo.app.core.quest.application.usecase;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.service.AssignQuestService;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
public class ConfirmAssignQuestUseCase {

	private final AssignQuestService assignQuestService;

	@AuditLog(action = "CONFIRM_ASSIGN_QUEST")
	public void confirm(UUID accessorId, UUID assignQuestId) {
		AssignQuest assignQuest = assignQuestService.find(assignQuestId);
		assignQuest.validateAuthority(accessorId);
		assignQuest.confirm();
	}
}
