package com.gomo.app.batch;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;

import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.model.participant.Participant;
import com.gomo.app.core.quest.domain.model.participant.QuestQuota;
import com.gomo.app.core.quest.domain.model.pool.ProcessingStatus;
import com.gomo.app.core.quest.domain.model.pool.QuestPool;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.repeat.RepeatQuest;
import com.gomo.app.core.quest.domain.repository.QuestPoolRepository;
import com.gomo.app.core.quest.domain.repository.RepeatQuestRepository;

public class RoutineAssignQuestProcessor implements ItemProcessor<Member, List<AssignQuest>> {

	private final RepeatQuestRepository repeatQuestRepository;
	private final QuestPoolRepository questPoolRepository;
	private final String questType;

	public RoutineAssignQuestProcessor(RepeatQuestRepository repeatQuestRepository, QuestPoolRepository questPoolRepository, String questType) {
		this.repeatQuestRepository = repeatQuestRepository;
		this.questPoolRepository = questPoolRepository;
		this.questType = questType;
	}

	@Override
	public List<AssignQuest> process(@NotNull Member member) {
		Participant participant = createParticipant(member);
		LocalDateTime startDateTime = LocalDateTime.now();
		UUID participantId = participant.getId();
		List<AssignQuest> participatingQuests = new ArrayList<>();
		DisplayOrder displayOrder = DisplayOrder.of(1);

		displayOrder = createAssignQuestFromRepeatQuest(participantId, QuestType.valueOf(questType), displayOrder, startDateTime, participatingQuests);
		createAssignQuestFromQuestPool(participant, participantId, participatingQuests, QuestType.valueOf(questType), displayOrder, startDateTime);
		return participatingQuests.isEmpty() ? null : participatingQuests;
	}

	@NotNull
	private Participant createParticipant(Member member) {
		return Participant.of(
			member.getId(),
			QuestQuota.of(
				member.getQuestProperty().dailyThreshold(),
				member.getQuestProperty().weeklyThreshold(),
				member.getQuestProperty().monthlyThreshold()
			)
		);
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
		long availableQuestPoolSize = questPoolRepository.countByQuestParticipantIdAndProcessingStatus(participantId, ProcessingStatus.UNUSED);
		QuestQuota questQuota = participant.getQuestQuota();
		int currentQuestSize = participatingQuests.size();
		if (!questQuota.isExceeded(questType, currentQuestSize)) {
			int availableQuestSize = questQuota.getAvailableQuestSize(questType, currentQuestSize);
			if (availableQuestSize <= availableQuestPoolSize) {
				List<QuestPool> questPools = questPoolRepository.findTopByQuestParticipantIdAndQuestTypeAndProcessingStatus(
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
