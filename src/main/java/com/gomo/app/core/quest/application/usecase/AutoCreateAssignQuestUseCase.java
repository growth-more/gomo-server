package com.gomo.app.core.quest.application.usecase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.core.quest.application.port.AutoCreateAssignQuestPortIn;
import com.gomo.app.core.quest.application.port.dto.ParticipantDto;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.model.participant.Participant;
import com.gomo.app.core.quest.domain.model.participant.QuestQuota;
import com.gomo.app.core.quest.domain.model.pool.ProcessingStatus;
import com.gomo.app.core.quest.domain.model.pool.QuestPool;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.repeat.RepeatQuest;
import com.gomo.app.core.quest.domain.repository.BulkAssignQuestRepository;
import com.gomo.app.core.quest.domain.repository.QuestPoolRepository;
import com.gomo.app.core.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implements the {@link AutoCreateAssignQuestPortIn} use case.
 * <p>
 * This service automatically creates {@link AssignQuest} entities for each participant.
 * It combines quests from two sources: user-defined {@link RepeatQuest}s and the general {@link QuestPool},
 * then saves them to the database in a single bulk operation.
 */
@Slf4j
@RequiredArgsConstructor
@ApplicationService
class AutoCreateAssignQuestUseCase implements AutoCreateAssignQuestPortIn {

	private final RepeatQuestRepository repeatQuestRepository;
	private final QuestPoolRepository questPoolRepository;
	private final BulkAssignQuestRepository bulkAssignQuestRepository;

	@AuditLog(action = "AUTO_CREATE_ASSIGN_QUESTS")
	@Override
	public void execute(List<ParticipantDto> participantDtos, String questType) {
		LocalDateTime now = LocalDateTime.now();
		List<AssignQuest> assignQuests = participantDtos.stream()
			.map(ParticipantDto::toParticipant)
			.flatMap(participant -> createAssignQuestsFromRepeatQuestAndQuestPool(questType, participant.getId(), now, participant).stream())
			.toList();
		bulkAssignQuestRepository.saveAll(assignQuests);
	}

	@NotNull
	private List<AssignQuest> createAssignQuestsFromRepeatQuestAndQuestPool(String questType, UUID participantId, LocalDateTime startDateTime, Participant participant) {
		List<AssignQuest> assignQuests = new ArrayList<>();
		DisplayOrder displayOrder = DisplayOrder.of(1);
		displayOrder = createAssignQuestFromRepeatQuest(participantId, QuestType.valueOf(questType), displayOrder, startDateTime, assignQuests);
		createAssignQuestFromQuestPool(participant, participantId, assignQuests, QuestType.valueOf(questType), displayOrder, startDateTime);
		return assignQuests;
	}

	private DisplayOrder createAssignQuestFromRepeatQuest(
		UUID participantId, QuestType questType, DisplayOrder displayOrder, LocalDateTime startDateTime, List<AssignQuest> participatingQuests) {
		List<RepeatQuest> repeatQuests = repeatQuestRepository.findRepeatQuestsByQuestType(participantId, questType);
		for (RepeatQuest repeatQuest : repeatQuests) {
			AssignQuest assignQuest = repeatQuest.createAssignQuest(displayOrder, startDateTime);
			participatingQuests.add(assignQuest);
			displayOrder = displayOrder.increase(1);
		}
		return displayOrder;
	}

	private void createAssignQuestFromQuestPool(
		Participant participant, UUID participantId, List<AssignQuest> participatingQuests, QuestType questType, DisplayOrder displayOrder, LocalDateTime startDateTime) {
		long availableQuestPoolSize = questPoolRepository.countByQuestParticipantIdAndQuestTypeAndProcessingStatus(participantId, questType, ProcessingStatus.UNUSED);
		QuestQuota questQuota = participant.getQuestQuota();
		int currentQuestSize = participatingQuests.size();
		if (!questQuota.isExceeded(questType, currentQuestSize)) {
			int availableQuestSize = questQuota.getAvailableQuestSize(questType, currentQuestSize);
			if (availableQuestSize <= availableQuestPoolSize) {
				List<QuestPool> questPools = questPoolRepository.findByQuestParticipantIdAndQuestTypeAndProcessingStatus(
					participantId, questType, ProcessingStatus.UNUSED, org.springframework.data.domain.PageRequest.of(0, availableQuestSize)
				);
				for (QuestPool questPool : questPools) {
					AssignQuest assignQuest = questPool.createAssignQuest(displayOrder, startDateTime);
					participatingQuests.add(assignQuest);
					displayOrder = displayOrder.increase(1);
				}
			} else {
				/*
					TODO [2025-10-12] jhl221123 : 현재 생성해야하는 퀘스트 수만큼 퀘스트 풀이 차있지 않은 경우
				 	1. 퀘스트 풀 배치 작업이 완료된 이후, 해당 배치가 실행되게 함으로 순서를 보장하는 것이 최선
				 	2. 예외 및 로그 처리 필요
				 */
			}
		}
	}
}
