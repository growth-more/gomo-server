package com.gomo.app.quest.application;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.event.Events;
import com.gomo.app.common.exception.DomainErrorCode;
import com.gomo.app.common.exception.NotFoundException;
import com.gomo.app.common.util.TimestampGenerator;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		publishQuestCompletionEvents(accessorId, assignQuest);
	}

	private void publishQuestCompletionEvents(UUID accessorId, AssignQuest assignQuest) {
		QuestReward questReward = questRewardService.create(assignQuest.getId(), assignQuest.getQuest().getType());
		long streakTraceId = TimestampGenerator.generate();

		logQuestCompletionEventPublications(accessorId, questReward, streakTraceId);
		Events.raise(ScoreQuestCompletedEvent.of(accessorId, assignQuest.getQuest().getSubjectId(), questReward.getScoreReward()));
		Events.raise(PointQuestCompletedEvent.of(ParticipantId.of(accessorId), questReward.getPointReward()));
		Events.raise(StreakQuestCompletedEvent.of(ParticipantId.of(accessorId), assignQuest.getQuest().getType(), assignQuest.getCompletedDateTime(), streakTraceId));
	}

	private void logQuestCompletionEventPublications(UUID accessorId, QuestReward questReward, long streakEventTraceId) {
		log.info("[CompleteAssignQuestUseCase] Raising ScoreQuestCompletedEvent with member id: {}, trace id: {}", accessorId, questReward.getScoreReward().getTraceId());
		log.info("[CompleteAssignQuestUseCase] Raising PointQuestCompletedEvent with member id: {}, trace id: {}", accessorId, questReward.getPointReward().getTraceId());
		log.info("[CompleteAssignQuestUseCase] Raising StreakQuestCompletedEvent with member id: {}, trace id: {}", accessorId, streakEventTraceId);
	}
}
