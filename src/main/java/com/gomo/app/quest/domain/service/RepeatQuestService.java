package com.gomo.app.quest.domain.service;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.common.DomainService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.displayorder.DisplayOrder;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.Quest;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.model.RepeatQuestId;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.quest.exception.RepeatQuestNotFoundException;
import com.gomo.app.quest.exception.code.RepeatQuestErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class RepeatQuestService {

	private final RepeatQuestRepository repeatQuestRepository;

	public RepeatQuest create(ParticipantId participantId, Quest quest) {
		int displayOrder = repeatQuestRepository.findMaxDisplayOrderByQuestType(participantId, quest.getType()) + 1;
		return repeatQuestRepository.save(createRepeatQuest(quest, displayOrder));
	}

	public RepeatQuest find(RepeatQuestId repeatQuestId) {
		return repeatQuestRepository.findById(repeatQuestId)
			.orElseThrow(() -> new RepeatQuestNotFoundException(RepeatQuestErrorCode.NOT_FOUND));
	}
	
	@NotNull
	private RepeatQuest createRepeatQuest(Quest quest, int displayOrder) {
		return RepeatQuest.of(
			RepeatQuestId.of(UUIDGenerator.generate()),
			quest,
			DisplayOrder.of(displayOrder)
		);
	}
}
