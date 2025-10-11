package com.gomo.app.core.quest.application.usecase;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.util.DateRangeCalculator;
import com.gomo.app.core.quest.application.port.dto.AssignQuestDto;
import com.gomo.app.core.quest.application.port.dto.ListAssignQuestDto;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.reward.policy.QuestPointPolicy;
import com.gomo.app.core.quest.domain.model.reward.policy.QuestScorePolicy;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.core.quest.domain.repository.QuestRewardPolicyRepository;
import com.gomo.app.core.quest.exception.QuestTypeConstraintViolationException;
import com.gomo.app.core.quest.exception.code.QuestTypeErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadAssignQuestUseCase {

	private final AssignQuestRepository assignQuestRepository;
	private final QuestRewardPolicyRepository questRewardPolicyRepository;

	public ListAssignQuestDto findAll(UUID participantId) {
		List<QuestPointPolicy> pointPolicies = questRewardPolicyRepository.findPointPolicies();
		List<QuestScorePolicy> scorePolicies = questRewardPolicyRepository.findScorePolicies();

		List<AssignQuestDto> dailyQuests = findAllByQuestType(participantId, QuestType.DAILY, pointPolicies, scorePolicies);
		List<AssignQuestDto> weeklyQuests = findAllByQuestType(participantId, QuestType.WEEKLY, pointPolicies, scorePolicies);
		List<AssignQuestDto> monthlyQuests = findAllByQuestType(participantId, QuestType.MONTHLY, pointPolicies, scorePolicies);
		return ListAssignQuestDto.of(dailyQuests, weeklyQuests, monthlyQuests);
	}

	private List<AssignQuestDto> findAllByQuestType(UUID participantId, QuestType questType, List<QuestPointPolicy> pointPolicies, List<QuestScorePolicy> scorePolicies) {
		return assignQuestRepository.findParticipatingQuestByQuestType(
				participantId,
				questType,
				DateRangeCalculator.startOf(LocalDate.now(), questType.name()),
				DateRangeCalculator.endOf(LocalDate.now(), questType.name())
			).stream()
			.map(assignQuest -> {
				int points = findPointByQuestType(pointPolicies, questType);
				int score = findScoreByQuestType(scorePolicies, questType);

				return AssignQuestDto.from(assignQuest, points, score);
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
