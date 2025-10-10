package com.gomo.app.core.quest.infrastructure.repository;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.reward.PointReward;
import com.gomo.app.core.quest.domain.model.reward.ScoreReward;
import com.gomo.app.core.quest.domain.model.reward.policy.QuestPointPolicy;
import com.gomo.app.core.quest.domain.model.reward.policy.QuestScorePolicy;
import com.gomo.app.core.quest.domain.repository.QuestRewardPolicyRepository;
import com.gomo.app.core.quest.domain.repository.QuestRewardRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class InMemoryQuestRewardRepository implements QuestRewardRepository {

	private final QuestRewardPolicyRepository questRewardPolicyRepository;
	private Map<QuestType, QuestScorePolicy> questScorePolicies;
	private Map<QuestType, QuestPointPolicy> questPointPolicies;

	@PostConstruct
	protected void initializeCaches() {
		questScorePolicies = questRewardPolicyRepository.findScorePolicies().stream()
			.collect(Collectors.toMap(QuestScorePolicy::getQuestType, Function.identity()));

		questPointPolicies = questRewardPolicyRepository.findPointPolicies().stream()
			.collect(Collectors.toMap(QuestPointPolicy::getQuestType, Function.identity()));
	}

	public ScoreReward findScoreReward(QuestType questType) {
		int score = questScorePolicies.get(questType).getScore();
		return ScoreReward.of(score);
	}

	public PointReward findPointReward(QuestType questType) {
		int point = questPointPolicies.get(questType).getPoints();
		return PointReward.of(point);
	}
}
