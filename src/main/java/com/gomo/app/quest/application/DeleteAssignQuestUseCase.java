package com.gomo.app.quest.application;

import java.util.UUID;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.exception.DomainErrorCode;
import com.gomo.app.common.exception.NotFoundException;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class DeleteAssignQuestUseCase {

	private final AssignQuestRepository assignQuestRepository;

	public void delete(UUID accessorId, AssignQuestId assignQuestId) {
		AssignQuest assignQuest = assignQuestRepository.findById(assignQuestId)
			.orElseThrow(() -> new NotFoundException(DomainErrorCode.NOT_FOUND, "Assign quest not found"));
		assignQuest.validateAuthority(accessorId);

		assignQuest.ensureNotConfirmed();
		assignQuest.ensureNotCompleted();
		assignQuestRepository.delete(assignQuest);
	}
}
