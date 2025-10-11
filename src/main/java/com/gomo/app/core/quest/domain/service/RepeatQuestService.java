package com.gomo.app.core.quest.domain.service;

import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.common.arch.DomainService;
import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.quest.domain.model.quest.Quest;
import com.gomo.app.core.quest.domain.model.repeat.RepeatQuest;
import com.gomo.app.core.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.core.quest.exception.RepeatQuestNotFoundException;
import com.gomo.app.core.quest.exception.code.RepeatQuestErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class RepeatQuestService {

	private final RepeatQuestRepository repeatQuestRepository;

	public RepeatQuest create(UUID participantId, Quest quest) {
		int displayOrder = repeatQuestRepository.findMaxDisplayOrderByQuestType(participantId, quest.getType()) + 1;
		return repeatQuestRepository.save(createRepeatQuest(quest, displayOrder));
	}

	public RepeatQuest find(UUID repeatQuestId) {
		return repeatQuestRepository.findById(repeatQuestId)
			.orElseThrow(() -> new RepeatQuestNotFoundException(RepeatQuestErrorCode.NOT_FOUND));
	}

	@NotNull
	private RepeatQuest createRepeatQuest(Quest quest, int displayOrder) {
		return RepeatQuest.of(UUIDGenerator.generate(), quest, DisplayOrder.of(displayOrder));
	}
}
