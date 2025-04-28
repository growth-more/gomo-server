package com.gomo.app.quest.application;

import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.model.RepeatQuestId;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.quest.domain.service.RepeatQuestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class DeleteRepeatQuestUseCase {

	private final RepeatQuestService repeatQuestService;
	private final RepeatQuestRepository repeatQuestRepository;

	public void delete(UUID accessorId, RepeatQuestId repeatQuestId) {
		RepeatQuest repeatQuest = repeatQuestService.find(repeatQuestId);
		repeatQuest.validateAuthority(accessorId);

		repeatQuestRepository.delete(repeatQuest);
	}
}
