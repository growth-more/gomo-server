package com.gomo.app.core.quest.domain.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.common.arch.DomainService;
import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.common.util.DateRangeCalculator;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.quest.domain.model.participant.ParticipantId;
import com.gomo.app.core.quest.domain.model.quest.Quest;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.model.assign.AssignQuestId;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.core.quest.exception.AssignQuestNotFoundException;
import com.gomo.app.core.quest.exception.code.AssignQuestErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class AssignQuestService {

	private final AssignQuestRepository assignQuestRepository;

	public AssignQuest create(ParticipantId participantId, Quest quest) {
		int displayOrder = findMaxDisplayOrderOfParticipatingQuest(participantId, quest.getType()) + 1;
		return assignQuestRepository.save(createAssignQuest(quest, displayOrder));
	}

	public AssignQuest find(AssignQuestId assignQuestId) {
		return assignQuestRepository.findById(assignQuestId)
			.orElseThrow(() -> new AssignQuestNotFoundException(AssignQuestErrorCode.NOT_FOUND));
	}

	@NotNull
	private AssignQuest createAssignQuest(Quest quest, int displayOrder) {
		return AssignQuest.of(
			AssignQuestId.of(UUIDGenerator.generate()),
			quest,
			false,
			DisplayOrder.of(displayOrder),
			LocalDateTime.now()
		);
	}

	private int findMaxDisplayOrderOfParticipatingQuest(ParticipantId participantId, QuestType questType) {
		LocalDate now = LocalDate.now();
		LocalDateTime startDateTime = DateRangeCalculator.startOf(now, questType.name());
		LocalDateTime endDateTime = DateRangeCalculator.endOf(now, questType.name());

		return assignQuestRepository.findMaxDisplayOrderOfParticipatingQuest(
			participantId,
			questType,
			startDateTime,
			endDateTime
		);
	}
}
