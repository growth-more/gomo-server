package com.gomo.app.core.quest.application.usecase;

import java.util.List;
import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.quest.application.port.dto.ListRepeatQuestDto;
import com.gomo.app.core.quest.application.port.dto.RepeatQuestDto;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.reward.QuestReward;
import com.gomo.app.core.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.core.quest.domain.service.QuestRewardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadRepeatQuestUseCase {

	private final QuestRewardService questRewardService;
	private final RepeatQuestRepository repeatQuestRepository;

	public ListRepeatQuestDto findAll(UUID participantId) {
		List<RepeatQuestDto> dailyRepeatQuests = findRepeatQuestResponses(participantId, QuestType.DAILY);
		List<RepeatQuestDto> weeklyRepeatQuests = findRepeatQuestResponses(participantId, QuestType.WEEKLY);
		List<RepeatQuestDto> monthlyRepeatQuests = findRepeatQuestResponses(participantId, QuestType.MONTHLY);
		return ListRepeatQuestDto.of(dailyRepeatQuests, weeklyRepeatQuests, monthlyRepeatQuests);
	}

	private List<RepeatQuestDto> findRepeatQuestResponses(UUID participantId, QuestType questType) {
		return repeatQuestRepository.findRepeatQuestsByQuestType(participantId, questType).stream()
			.map(assignQuest -> {
				QuestReward questReward = questRewardService.find(questType);
				return RepeatQuestDto.from(assignQuest, questReward.pointValue(), questReward.scoreValue());
			}).toList();
	}
}
