package com.gomo.app.quest.infrastructure;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.gomo.app.quest.domain.model.PointReward;
import com.gomo.app.quest.domain.model.QuestPointPolicy;
import com.gomo.app.quest.domain.model.QuestScorePolicy;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.ScoreReward;
import com.gomo.app.quest.domain.policy.QuestRewardPolicyProvider;
import com.gomo.app.quest.domain.repository.QuestRewardPolicyRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class InMemoryQuestRewardPolicyProvider implements QuestRewardPolicyProvider {

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

	public ScoreReward getScoreReward(QuestType questType) {
		int score = questScorePolicies.get(questType).getScore();
		return ScoreReward.of(score);
	}

	public PointReward getPointReward(QuestType questType) {
		int point = questPointPolicies.get(questType).getPoints();
		return PointReward.of(point);
	}
}
