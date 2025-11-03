package com.gomo.app.core.quest.application.service;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.quest.application.port.dto.AssignQuestDetailDto;
import com.gomo.app.core.quest.application.port.in.AssignQuestReRoller;
import com.gomo.app.core.quest.domain.exception.QuestPoolNotFoundException;
import com.gomo.app.core.quest.domain.exception.code.QuestPoolErrorCode;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.model.pool.ProcessingStatus;
import com.gomo.app.core.quest.domain.model.pool.QuestPool;
import com.gomo.app.core.quest.domain.model.pool.SourceType;
import com.gomo.app.core.quest.domain.model.reward.QuestReward;
import com.gomo.app.core.quest.domain.repository.QuestPoolRepository;
import com.gomo.app.core.quest.domain.service.QuestRewardProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class AssignQuestReRollService implements AssignQuestReRoller {

	private final AssignQuestService assignQuestService;
	private final QuestRewardProvider questRewardProvider;
	private final QuestPoolRepository questPoolRepository;

	@Override
	@AuditLog(action = "ASSIGN_QUEST_RE_ROLL")
	public AssignQuestDetailDto reRoll(UUID participantId, UUID assignQuestId) {
		AssignQuest assignQuest = assignQuestService.readById(assignQuestId);
		QuestPool questPool = questPoolRepository.findFirstByQuestParticipantIdAndQuestTypeAndSourceTypeAndProcessingStatus(
			participantId,
			assignQuest.questType(),
			SourceType.AI,
			ProcessingStatus.UNUSED
		).orElseThrow(() -> new QuestPoolNotFoundException(QuestPoolErrorCode.NOT_FOUND));
		assignQuest.updateQuest(questPool.getQuest());
		questPool.updateProcessingStatus(ProcessingStatus.ASSIGNED);
		QuestReward questReward = questRewardProvider.provide(assignQuest.questType());
		return AssignQuestDetailDto.from(assignQuest, questReward.pointValue(), questReward.scoreValue());
	}
}
