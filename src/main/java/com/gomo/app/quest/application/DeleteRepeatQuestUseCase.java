package com.gomo.app.quest.application;

import java.util.UUID;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.exception.DomainErrorCode;
import com.gomo.app.common.exception.NotFoundException;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.model.RepeatQuestId;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class DeleteRepeatQuestUseCase {

	private final RepeatQuestRepository repeatQuestRepository;

	public void delete(UUID accessorId, RepeatQuestId repeatQuestId) {
		RepeatQuest repeatQuest = repeatQuestRepository.findById(repeatQuestId)
			.orElseThrow(() -> new NotFoundException(DomainErrorCode.NOT_FOUND, "RepeatQuest not found with id: " + repeatQuestId.getId()));
		repeatQuest.validateAuthority(accessorId);

		repeatQuestRepository.delete(repeatQuest);
	}
}
