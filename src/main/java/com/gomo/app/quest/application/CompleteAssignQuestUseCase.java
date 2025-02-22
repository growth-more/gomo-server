package com.gomo.app.quest.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.event.EventEntry;
import com.gomo.app.common.event.EventEntryRepository;
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

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class CompleteAssignQuestUseCase {

	private final QuestRewardService questRewardService;
	private final AssignQuestRepository assignQuestRepository;
	private final EventEntryRepository eventEntryRepository;

	public void complete(UUID accessorId, AssignQuestId assignQuestId, CompleteAssignQuestRequest request) {
		AssignQuest assignQuest = assignQuestRepository.findById(assignQuestId)
			.orElseThrow(() -> new NotFoundException(DomainErrorCode.NOT_FOUND, "Assign quest not found"));
		assignQuest.validateAuthority(accessorId);

		assignQuest.complete(CompletionProof.of(request.getProof()), LocalDateTime.now());
		createQuestCompletionEvents(accessorId, assignQuest);
	}

	private void createQuestCompletionEvents(UUID accessorId, AssignQuest assignQuest) {
		QuestReward questReward = questRewardService.create(assignQuest.getId(), assignQuest.getQuest().getType());
		long completedTime = TimestampGenerator.generate();

		eventEntryRepository.saveAll(
			List.of(
				createScoreEventEntry(accessorId, assignQuest, questReward, completedTime),
				createPointEventEntry(accessorId, questReward, completedTime),
				createStreakEventEntry(accessorId, assignQuest, completedTime)
			)
		);
	}

	@NotNull
	private EventEntry createStreakEventEntry(UUID accessorId, AssignQuest assignQuest, long completedTime) {
		StreakQuestCompletedEvent streakEvent = StreakQuestCompletedEvent.of(
			ParticipantId.of(accessorId),
			assignQuest.getQuest().getType(),
			assignQuest.getCompletedDateTime(),
			completedTime
		);
		return EventEntry.of(streakEvent.getClass().getSimpleName(), streakEvent.toJson(), completedTime);
	}

	@NotNull
	private EventEntry createPointEventEntry(UUID accessorId, QuestReward questReward, long completedTime) {
		PointQuestCompletedEvent pointEvent = PointQuestCompletedEvent.of(
			ParticipantId.of(accessorId),
			questReward.getPointReward(),
			completedTime
		);
		return EventEntry.of(pointEvent.getClass().getSimpleName(), pointEvent.toJson(), completedTime);
	}

	@NotNull
	private EventEntry createScoreEventEntry(UUID accessorId, AssignQuest assignQuest, QuestReward questReward, long completedTime) {
		ScoreQuestCompletedEvent scoreEvent = ScoreQuestCompletedEvent.of(
			accessorId,
			assignQuest.getQuest().getSubjectId(),
			questReward.getScoreReward(),
			completedTime
		);
		return EventEntry.of(scoreEvent.getClass().getSimpleName(), scoreEvent.toJson(), completedTime);
	}
}
