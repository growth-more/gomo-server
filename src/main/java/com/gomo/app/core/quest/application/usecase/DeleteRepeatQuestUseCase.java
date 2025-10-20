package com.gomo.app.core.quest.application.usecase;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.quest.domain.model.repeat.RepeatQuest;
import com.gomo.app.core.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.core.quest.domain.service.RepeatQuestService;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
public class DeleteRepeatQuestUseCase {

	private final RepeatQuestService repeatQuestService;
	private final RepeatQuestRepository repeatQuestRepository;

	@AuditLog(action = "DELETE_REPEAT_QUEST")
	public void delete(UUID participantId, UUID repeatQuestId) {
		RepeatQuest repeatQuest = repeatQuestService.find(repeatQuestId);
		repeatQuest.validateAuthority(participantId);
		repeatQuestRepository.delete(repeatQuest);
	}
}
