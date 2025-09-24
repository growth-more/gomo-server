package com.gomo.app.core.quest.application;

import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.support.logging.AuditLog;
import com.gomo.app.core.quest.domain.model.RepeatQuest;
import com.gomo.app.core.quest.domain.model.RepeatQuestId;
import com.gomo.app.core.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.core.quest.domain.service.RepeatQuestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class DeleteRepeatQuestUseCase {

	private final RepeatQuestService repeatQuestService;
	private final RepeatQuestRepository repeatQuestRepository;

	@AuditLog(action = "DELETE_REPEAT_QUEST")
	public void delete(UUID participantId, UUID repeatQuestId) {
		RepeatQuest repeatQuest = repeatQuestService.find(RepeatQuestId.of(repeatQuestId));
		repeatQuest.validateAuthority(participantId);
		repeatQuestRepository.delete(repeatQuest);
	}
}
