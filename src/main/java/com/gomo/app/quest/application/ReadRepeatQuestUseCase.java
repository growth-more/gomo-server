package com.gomo.app.quest.application;

import java.util.List;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.QuestPointPolicy;
import com.gomo.app.quest.domain.model.QuestScorePolicy;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.repository.QuestRewardPolicyRepository;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.quest.exception.QuestTypeConstraintViolationException;
import com.gomo.app.quest.exception.code.QuestTypeErrorCode;
import com.gomo.app.quest.presentation.response.ListRepeatQuestResponse;
import com.gomo.app.quest.presentation.response.ReadRepeatQuestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadRepeatQuestUseCase {

	private final RepeatQuestRepository repeatQuestRepository;
	private final QuestRewardPolicyRepository questRewardPolicyRepository;

	public ListRepeatQuestResponse findAll(ParticipantId participantId) {
		List<QuestPointPolicy> pointPolicies = questRewardPolicyRepository.findPointPolicies();
		List<QuestScorePolicy> scorePolicies = questRewardPolicyRepository.findScorePolicies();

		List<ReadRepeatQuestResponse> dailyRepeatQuests = findRepeatQuestResponses(participantId, QuestType.DAILY, pointPolicies, scorePolicies);
		List<ReadRepeatQuestResponse> weeklyRepeatQuests = findRepeatQuestResponses(participantId, QuestType.WEEKLY, pointPolicies, scorePolicies);
		List<ReadRepeatQuestResponse> monthlyRepeatQuests = findRepeatQuestResponses(participantId, QuestType.MONTHLY, pointPolicies, scorePolicies);

		return ListRepeatQuestResponse.of(dailyRepeatQuests, weeklyRepeatQuests, monthlyRepeatQuests);
	}

	private List<ReadRepeatQuestResponse> findRepeatQuestResponses(
		ParticipantId participantId,
		QuestType questType,
		List<QuestPointPolicy> pointPolicies,
		List<QuestScorePolicy> scorePolicies
	) {
		return repeatQuestRepository.findRepeatQuestsByQuestType(
				participantId,
				questType
			).stream()
			.map(assignQuest -> {
				int points = findPointByQuestType(pointPolicies, questType);
				int score = findScoreByQuestType(scorePolicies, questType);

				return ReadRepeatQuestResponse.of(assignQuest, points, score);
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
