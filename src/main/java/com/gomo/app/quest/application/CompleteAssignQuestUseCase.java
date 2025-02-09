package com.gomo.app.quest.application;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.event.Events;
import com.gomo.app.common.exception.DomainErrorCode;
import com.gomo.app.common.exception.NotFoundException;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.model.CompletionProof;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.QuestReward;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.domain.service.QuestRewardService;
import com.gomo.app.quest.event.PointQuestCompletedEvent;
import com.gomo.app.quest.event.ScoreQuestCompletedEvent;
import com.gomo.app.quest.event.StreakQuestCompletedEvent;
import com.gomo.app.quest.presentation.request.CompleteAssignQuestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class CompleteAssignQuestUseCase {

	private final QuestRewardService questRewardService;
	private final AssignQuestRepository assignQuestRepository;

	public void complete(UUID accessorId, AssignQuestId assignQuestId, CompleteAssignQuestRequest request) {
		AssignQuest assignQuest = assignQuestRepository.findById(assignQuestId)
			.orElseThrow(() -> new NotFoundException(DomainErrorCode.NOT_FOUND, "Assign quest not found"));
		assignQuest.validateAuthority(accessorId);

		assignQuest.complete(CompletionProof.of(request.getProof()), LocalDateTime.now());

		QuestReward questReward = questRewardService.create(assignQuest.getId(), assignQuest.getQuest().getType());
		Events.raise(ScoreQuestCompletedEvent.of(assignQuest.getQuest().getSubjectId(), questReward.getScoreReward()));
		Events.raise(PointQuestCompletedEvent.of(ParticipantId.of(accessorId), questReward.getPointReward()));
		Events.raise(StreakQuestCompletedEvent.of(ParticipantId.of(accessorId), assignQuest.getQuest().getType(), assignQuest.getCompletedDateTime()));
	}
}
