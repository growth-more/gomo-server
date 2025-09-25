package com.gomo.app.core.quest.application.usecase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.util.JsonParser;
import com.gomo.app.common.util.TimestampGenerator;
import com.gomo.app.core.quest.application.port.command.CompleteAssignQuestCommand;
import com.gomo.app.core.quest.domain.model.AssignQuest;
import com.gomo.app.core.quest.domain.model.AssignQuestId;
import com.gomo.app.core.quest.domain.model.CompletionProof;
import com.gomo.app.core.quest.domain.model.ParticipantId;
import com.gomo.app.core.quest.domain.model.QuestReward;
import com.gomo.app.core.quest.domain.service.AssignQuestService;
import com.gomo.app.core.quest.domain.service.QuestRewardService;
import com.gomo.app.core.quest.event.PointQuestCompletedEvent;
import com.gomo.app.core.quest.event.ScoreQuestCompletedEvent;
import com.gomo.app.core.quest.event.StreakQuestCompletedEvent;
import com.gomo.app.support.event.EventEntry;
import com.gomo.app.support.event.EventEntryRepository;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@ApplicationService
@Transactional
public class CompleteAssignQuestUseCase {

	private final AssignQuestService assignQuestService;
	private final QuestRewardService questRewardService;
	private final EventEntryRepository eventEntryRepository;

	@AuditLog(action = "COMPLETE_ASSIGN_QUEST")
	public void complete(CompleteAssignQuestCommand command) {
		AssignQuest assignQuest = assignQuestService.find(AssignQuestId.of(command.assignQuestId()));
		assignQuest.validateAuthority(command.participantId());
		assignQuest.complete(CompletionProof.of(command.proof()), LocalDateTime.now());
		createQuestCompletionEvents(command.participantId(), assignQuest);
	}

	private void createQuestCompletionEvents(UUID participantId, AssignQuest assignQuest) {
		QuestReward questReward = questRewardService.create(assignQuest.getId(), assignQuest.getQuest().getType());
		long completedTime = TimestampGenerator.generate();

		eventEntryRepository.saveAll(
			List.of(
				createScoreEventEntry(participantId, assignQuest, questReward, completedTime),
				createPointEventEntry(participantId, questReward, completedTime),
				createStreakEventEntry(participantId, assignQuest, completedTime)
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
		return EventEntry.of(streakEvent.getClass().getSimpleName(), JsonParser.toJson(streakEvent), completedTime);
	}

	@NotNull
	private EventEntry createPointEventEntry(UUID accessorId, QuestReward questReward, long completedTime) {
		PointQuestCompletedEvent pointEvent = PointQuestCompletedEvent.of(
			ParticipantId.of(accessorId),
			questReward.getPointReward(),
			completedTime
		);
		return EventEntry.of(pointEvent.getClass().getSimpleName(), JsonParser.toJson(pointEvent), completedTime);
	}

	@NotNull
	private EventEntry createScoreEventEntry(UUID accessorId, AssignQuest assignQuest, QuestReward questReward, long completedTime) {
		ScoreQuestCompletedEvent scoreEvent = ScoreQuestCompletedEvent.of(
			accessorId,
			assignQuest.getQuest().getSubjectId(),
			questReward.getScoreReward(),
			completedTime
		);
		return EventEntry.of(scoreEvent.getClass().getSimpleName(), JsonParser.toJson(scoreEvent), completedTime);
	}
}
