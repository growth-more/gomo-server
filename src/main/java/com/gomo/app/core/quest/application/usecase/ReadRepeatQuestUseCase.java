package com.gomo.app.core.quest.application.usecase;

import java.util.List;
import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.quest.application.port.dto.ListRepeatQuestDto;
import com.gomo.app.core.quest.application.port.dto.RepeatQuestDto;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.reward.policy.QuestPointPolicy;
import com.gomo.app.core.quest.domain.model.reward.policy.QuestScorePolicy;
import com.gomo.app.core.quest.domain.repository.QuestRewardPolicyRepository;
import com.gomo.app.core.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.core.quest.exception.QuestTypeConstraintViolationException;
import com.gomo.app.core.quest.exception.code.QuestTypeErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadRepeatQuestUseCase {

	private final RepeatQuestRepository repeatQuestRepository;
	private final QuestRewardPolicyRepository questRewardPolicyRepository;

	public ListRepeatQuestDto findAll(UUID participantId) {
		List<QuestPointPolicy> pointPolicies = questRewardPolicyRepository.findPointPolicies();
		List<QuestScorePolicy> scorePolicies = questRewardPolicyRepository.findScorePolicies();

		List<RepeatQuestDto> dailyRepeatQuests = findRepeatQuestResponses(participantId, QuestType.DAILY, pointPolicies, scorePolicies);
		List<RepeatQuestDto> weeklyRepeatQuests = findRepeatQuestResponses(participantId, QuestType.WEEKLY, pointPolicies, scorePolicies);
		List<RepeatQuestDto> monthlyRepeatQuests = findRepeatQuestResponses(participantId, QuestType.MONTHLY, pointPolicies, scorePolicies);
		return ListRepeatQuestDto.of(dailyRepeatQuests, weeklyRepeatQuests, monthlyRepeatQuests);
	}

	private List<RepeatQuestDto> findRepeatQuestResponses(
		UUID participantId,
		QuestType questType,
		List<QuestPointPolicy> pointPolicies,
		List<QuestScorePolicy> scorePolicies
	) {
		return repeatQuestRepository.findRepeatQuestsByQuestType(participantId, questType).stream()
			.map(assignQuest -> {
				int points = findPointByQuestType(pointPolicies, questType);
				int score = findScoreByQuestType(scorePolicies, questType);
				return RepeatQuestDto.from(assignQuest, points, score);
			}).toList();
	}

	private int findPointByQuestType(List<QuestPointPolicy> pointPolicies, QuestType questType) {
		return pointPolicies.stream()
			.filter(policy -> policy.getQuestType() == questType)
			.map(QuestPointPolicy::getPoints)
			.findFirst()
			.orElseThrow(() -> new QuestTypeConstraintViolationException(QuestTypeErrorCode.UNEXPECTED));
	}

	private int findScoreByQuestType(List<QuestScorePolicy> scorePolicies, QuestType questType) {
		return scorePolicies.stream()
			.filter(policy -> policy.getQuestType() == questType)
			.map(QuestScorePolicy::getScore)
			.findFirst()
			.orElseThrow(() -> new QuestTypeConstraintViolationException(QuestTypeErrorCode.UNEXPECTED));
	}
}
