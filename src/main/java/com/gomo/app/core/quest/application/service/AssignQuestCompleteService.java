package com.gomo.app.core.quest.application.service;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.common.util.JsonParser;
import com.gomo.app.common.util.TimestampGenerator;
import com.gomo.app.core.quest.application.port.command.CompleteAssignQuestCommand;
import com.gomo.app.core.quest.application.port.in.AssignQuestCompleter;
import com.gomo.app.core.quest.domain.event.CompleteQuestEvent;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.model.assign.CompletionProof;
import com.gomo.app.core.quest.domain.model.reward.QuestReward;
import com.gomo.app.core.quest.domain.service.QuestRewardProvider;
import com.gomo.app.support.evententry.application.port.CreateEventEntryPortIn;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class AssignQuestCompleteService implements AssignQuestCompleter {

	private final AssignQuestService assignQuestService;
	private final QuestRewardProvider questRewardProvider;
	private final CreateEventEntryPortIn createEventEntryPortIn;

	@Override
	@AuditLog(action = "ASSIGN_QUEST_COMPLETE")
	public void complete(CompleteAssignQuestCommand command) {
		AssignQuest assignQuest = assignQuestService.readById(command.assignQuestId());
		assignQuest.validateAuthority(command.participantId());
		assignQuest.complete(CompletionProof.of(command.proof()), command.completedDateTime());
		createQuestCompletionEvent(command.participantId(), assignQuest);
	}

	private void createQuestCompletionEvent(UUID participantId, AssignQuest assignQuest) {
		QuestReward questReward = questRewardProvider.provide(assignQuest.questType());
		long timestamp = TimestampGenerator.generate();
		CompleteQuestEvent completeQuestEvent = CompleteQuestEvent.of(
			participantId,
			assignQuest.subjectId(),
			assignQuest.questType().name(),
			questReward.scoreValue(),
			questReward.pointValue(),
			assignQuest.getCompletedDateTime(),
			timestamp
		);
		createEventEntryPortIn.create(completeQuestEvent.getClass().getName(), JsonParser.toJson(completeQuestEvent), timestamp);
	}
}
