package com.gomo.app.core.quest.application.usecase;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.util.DateRangeCalculator;
import com.gomo.app.core.quest.application.port.dto.AssignQuestDto;
import com.gomo.app.core.quest.application.port.dto.ListAssignQuestDto;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.reward.QuestReward;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.core.quest.domain.service.QuestRewardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadAssignQuestUseCase {

	private final QuestRewardService questRewardService;
	private final AssignQuestRepository assignQuestRepository;

	public ListAssignQuestDto findAll(UUID participantId) {
		List<AssignQuestDto> dailyQuests = findAllByQuestType(participantId, QuestType.DAILY);
		List<AssignQuestDto> weeklyQuests = findAllByQuestType(participantId, QuestType.WEEKLY);
		List<AssignQuestDto> monthlyQuests = findAllByQuestType(participantId, QuestType.MONTHLY);
		return ListAssignQuestDto.of(dailyQuests, weeklyQuests, monthlyQuests);
	}

	private List<AssignQuestDto> findAllByQuestType(UUID participantId, QuestType questType) {
		return assignQuestRepository.findParticipatingQuestByQuestType(
			participantId,
			questType,
			DateRangeCalculator.startOf(LocalDate.now(), questType.name()),
			DateRangeCalculator.endOf(LocalDate.now(), questType.name())
		).stream().map(assignQuest -> {
			QuestReward questReward = questRewardService.find(questType);
			return AssignQuestDto.from(assignQuest, questReward.pointValue(), questReward.scoreValue());
		}).toList();
	}
}
