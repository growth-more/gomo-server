package com.gomo.app.core.quest.application.usecase;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.util.JsonParser;
import com.gomo.app.common.util.TimestampGenerator;
import com.gomo.app.core.quest.application.port.command.CompleteAssignQuestCommand;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.model.assign.AssignQuestId;
import com.gomo.app.core.quest.domain.model.assign.CompletionProof;
import com.gomo.app.core.quest.domain.model.reward.QuestReward;
import com.gomo.app.core.quest.domain.service.AssignQuestService;
import com.gomo.app.core.quest.domain.service.QuestRewardService;
import com.gomo.app.core.quest.event.CompleteQuestEvent;
import com.gomo.app.support.evententry.application.port.CreateEventEntryPortIn;
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
	private final CreateEventEntryPortIn createEventEntryPortIn;

	@AuditLog(action = "COMPLETE_ASSIGN_QUEST")
	public void complete(CompleteAssignQuestCommand command) {
		AssignQuest assignQuest = assignQuestService.find(AssignQuestId.of(command.assignQuestId()));
		assignQuest.validateAuthority(command.participantId());
		assignQuest.complete(CompletionProof.of(command.proof()), command.completedDateTime());
		createQuestCompletionEvent(command.participantId(), assignQuest);
	}

	private void createQuestCompletionEvent(UUID participantId, AssignQuest assignQuest) {
		QuestReward questReward = questRewardService.create(assignQuest.getId(), assignQuest.questType());
		long timestamp = TimestampGenerator.generate();
		CompleteQuestEvent completeQuestEvent = CompleteQuestEvent.of(
			participantId,
			assignQuest.subjectId(),
			assignQuest.questType().name(),
			questReward.scoreReward(),
			questReward.pointReward(),
			assignQuest.getCompletedDateTime(),
			timestamp
		);
		createEventEntryPortIn.create(completeQuestEvent.getClass().getName(), JsonParser.toJson(completeQuestEvent), timestamp);
	}
}
