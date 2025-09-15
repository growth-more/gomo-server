package com.gomo.app.quest.application;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.logging.AuditLog;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.service.AssignQuestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class ConfirmAssignQuestUseCase {

	private final AssignQuestService assignQuestService;

	@AuditLog(action = "CONFIRM_ASSIGN_QUEST")
	public void confirm(UUID accessorId, UUID assignQuestId) {
		AssignQuest assignQuest = assignQuestService.find(AssignQuestId.of(assignQuestId));
		assignQuest.validateAuthority(accessorId);

		assignQuest.confirm();
	}
}
