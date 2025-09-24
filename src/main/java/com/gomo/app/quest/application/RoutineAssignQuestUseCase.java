package com.gomo.app.quest.application;

import static com.gomo.app.quest.domain.model.QuestType.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.displayorder.DisplayOrder;
import com.gomo.app.quest.application.port.ReadActiveParticipantPortOut;
import com.gomo.app.quest.application.port.RoutineAssignQuestPortIn;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.Participant;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.ProcessingStatus;
import com.gomo.app.quest.domain.model.QuestPool;
import com.gomo.app.quest.domain.model.QuestQuota;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.domain.repository.QuestPoolRepository;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class RoutineAssignQuestUseCase implements RoutineAssignQuestPortIn {

	private final ReadActiveParticipantPortOut readActiveParticipantPortOut;
	private final AssignQuestRepository assignQuestRepository;
	private final RepeatQuestRepository repeatQuestRepository;
	private final QuestPoolRepository questPoolRepository;

	@Override
	public void createForActiveMembers(String questType) {
		LocalDateTime startDateTime = LocalDateTime.now();
		List<Participant> participants = readActiveParticipantPortOut.findAll(startDateTime.toLocalDate().minusDays(1)).stream()
			.map(dto -> Participant.of(
				ParticipantId.of(dto.participantId()),
				QuestQuota.of(dto.dailyQuota(), dto.weeklyQuota(), dto.monthlyQuota())
			)).toList();

		for (Participant participant : participants) {
			ParticipantId participantId = ParticipantId.of(participant.uuid());
			QuestType type = valueOf(questType);
			List<RepeatQuest> repeatQuests = repeatQuestRepository.findRepeatQuestsByQuestType(participantId, type);
			assignQuestRepository.saveAll(createParticipatingQuest(participant, startDateTime, type, repeatQuests));
		}
	}

	@NotNull
	private List<AssignQuest> createParticipatingQuest(Participant participant, LocalDateTime startDateTime, QuestType questType, List<RepeatQuest> repeatQuests) {
		ParticipantId participantId = ParticipantId.of(participant.uuid());
		List<AssignQuest> participatingQuests = new ArrayList<>();
		DisplayOrder displayOrder = DisplayOrder.of(1);

		for (RepeatQuest repeatQuest : repeatQuests) {
			AssignQuest assignQuest = repeatQuest.createAssignQuest(displayOrder, startDateTime);
			participatingQuests.add(assignQuest);
			displayOrder = displayOrder.increase(1);
		}

		long availableQuestPoolSize = questPoolRepository.countByQuestParticipantIdAndProcessingStatus(participantId, ProcessingStatus.UNUSED);
		QuestQuota questQuota = participant.getQuestQuota();
		int currentQuestSize = participatingQuests.size();
		if (!questQuota.isExceeded(questType, currentQuestSize)) {
			int availableQuestSize = questQuota.getAvailableQuestSize(questType, currentQuestSize);
			if (availableQuestSize <= availableQuestPoolSize) {
				List<QuestPool> questPools = questPoolRepository.findTopByQuestParticipantIdAndQuestTypeAndProcessingStatus(participantId, questType,
					ProcessingStatus.UNUSED, PageRequest.of(0, availableQuestSize));
				for (QuestPool questPool : questPools) {
					AssignQuest assignQuest = questPool.createAssignQuest(displayOrder, startDateTime);
					participatingQuests.add(assignQuest);
					displayOrder = displayOrder.increase(1);
				}
			} else {
				// todo jhl221123: 현재 생성해야하는 퀘스트 수만큼 퀘스트 풀이 차있지 않은 경우, 퀘스트 풀을 채우도록 이벤트 발행 필요
			}
		}

		return participatingQuests;
	}
}
