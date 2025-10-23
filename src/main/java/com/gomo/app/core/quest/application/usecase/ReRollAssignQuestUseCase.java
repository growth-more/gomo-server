package com.gomo.app.core.quest.application.usecase;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.quest.application.port.dto.AssignQuestDto;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.model.pool.ProcessingStatus;
import com.gomo.app.core.quest.domain.model.pool.QuestPool;
import com.gomo.app.core.quest.domain.model.pool.SourceType;
import com.gomo.app.core.quest.domain.model.reward.QuestReward;
import com.gomo.app.core.quest.domain.repository.QuestPoolRepository;
import com.gomo.app.core.quest.domain.service.AssignQuestService;
import com.gomo.app.core.quest.domain.service.QuestRewardService;
import com.gomo.app.core.quest.exception.QuestPoolNotFoundException;
import com.gomo.app.core.quest.exception.code.QuestPoolErrorCode;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
public class ReRollAssignQuestUseCase {

	private final QuestPoolRepository questPoolRepository;
	private final AssignQuestService assignQuestService;
	private final QuestRewardService questRewardService;

	@AuditLog(action = "RE_ROLL_ASSIGN_QUEST")
	public AssignQuestDto reRoll(UUID participantId, UUID assignQuestId) {
		AssignQuest assignQuest = assignQuestService.find(assignQuestId);
		QuestPool questPool = questPoolRepository.findFirstByQuestParticipantIdAndQuestTypeAndSourceTypeAndProcessingStatus(
			participantId,
			assignQuest.questType(),
			SourceType.AI,
			ProcessingStatus.UNUSED
		).orElseThrow(() -> new QuestPoolNotFoundException(QuestPoolErrorCode.NOT_FOUND));
		assignQuest.updateQuest(questPool.getQuest());
		questPool.updateProcessingStatus(ProcessingStatus.ASSIGNED);
		QuestReward questReward = questRewardService.find(assignQuest.questType());
		return AssignQuestDto.from(assignQuest, questReward.pointValue(), questReward.scoreValue());
	}
}
