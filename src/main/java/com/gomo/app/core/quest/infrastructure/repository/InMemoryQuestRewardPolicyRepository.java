package com.gomo.app.core.quest.infrastructure.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.reward.policy.QuestPointPolicy;
import com.gomo.app.core.quest.domain.model.reward.policy.QuestScorePolicy;
import com.gomo.app.core.quest.domain.repository.QuestRewardPolicyRepository;

@Repository
public class InMemoryQuestRewardPolicyRepository implements QuestRewardPolicyRepository {

	@Override
	public List<QuestScorePolicy> findScorePolicies() {
		return List.of(
			QuestScorePolicy.of(QuestType.DAILY, 2),
			QuestScorePolicy.of(QuestType.WEEKLY, 20),
			QuestScorePolicy.of(QuestType.MONTHLY, 100)
		);
	}

	@Override
	public List<QuestPointPolicy> findPointPolicies() {
		return List.of(
			QuestPointPolicy.of(QuestType.DAILY, 10),
			QuestPointPolicy.of(QuestType.WEEKLY, 150),
			QuestPointPolicy.of(QuestType.MONTHLY, 1500)
		);
	}
}
