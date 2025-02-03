package com.gomo.app.quest.application;

import java.time.LocalDate;
import java.util.List;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.util.DateRangeCalculator;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.QuestPointPolicy;
import com.gomo.app.quest.domain.model.QuestScorePolicy;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.domain.repository.QuestRewardPolicyRepository;
import com.gomo.app.quest.presentation.response.ListAssignQuestResponse;
import com.gomo.app.quest.presentation.response.ReadAssignQuestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadAssignQuestUseCase {

	private final AssignQuestRepository assignQuestRepository;
	private final QuestRewardPolicyRepository questRewardPolicyRepository;

	public ListAssignQuestResponse findAll(ParticipantId participantId) {
		List<QuestPointPolicy> pointPolicies = questRewardPolicyRepository.findPointPolicies();
		List<QuestScorePolicy> scorePolicies = questRewardPolicyRepository.findScorePolicies();

		List<ReadAssignQuestResponse> dailyQuests = findAllByQuestType(participantId, QuestType.DAILY, pointPolicies, scorePolicies);
		List<ReadAssignQuestResponse> weeklyQuests = findAllByQuestType(participantId, QuestType.WEEKLY, pointPolicies, scorePolicies);
		List<ReadAssignQuestResponse> monthlyQuests = findAllByQuestType(participantId, QuestType.MONTHLY, pointPolicies, scorePolicies);

		return ListAssignQuestResponse.of(dailyQuests, weeklyQuests, monthlyQuests);
	}

	private List<ReadAssignQuestResponse> findAllByQuestType(
		ParticipantId participantId,
		QuestType questType,
		List<QuestPointPolicy> pointPolicies,
		List<QuestScorePolicy> scorePolicies
	) {
		return assignQuestRepository.findParticipatingQuestByQuestType(
				participantId,
				questType,
				DateRangeCalculator.startOf(LocalDate.now(), questType.name()),
				DateRangeCalculator.endOf(LocalDate.now(), questType.name())
			).stream()
			.map(assignQuest -> {
				int points = findPointByQuestType(pointPolicies, questType);
				int score = findScoreByQuestType(scorePolicies, questType);

				return ReadAssignQuestResponse.of(assignQuest, points, score);
			}).toList();
	}

	private int findPointByQuestType(List<QuestPointPolicy> pointPolicies, QuestType questType) {
		return pointPolicies.stream()
			.filter(policy -> policy.getQuestType() == questType)
			.map(QuestPointPolicy::getPoints)
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("No point policy found for quest type " + questType));
	}

	private int findScoreByQuestType(List<QuestScorePolicy> scorePolicies, QuestType questType) {
		return scorePolicies.stream()
			.filter(policy -> policy.getQuestType() == questType)
			.map(QuestScorePolicy::getScore)
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("No score policy found for quest type " + questType));
	}
}
