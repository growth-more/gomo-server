package com.gomo.app.core.quest.infrastructure.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.reward.PointReward;
import com.gomo.app.core.quest.domain.model.reward.QuestReward;
import com.gomo.app.core.quest.domain.model.reward.QuestRewardPolicy;
import com.gomo.app.core.quest.domain.model.reward.ScoreReward;
import com.gomo.app.core.quest.domain.repository.QuestRewardPolicyRepository;

@Repository
public class InMemoryQuestRewardPolicyRepository implements QuestRewardPolicyRepository {

	@Override
	public List<QuestRewardPolicy> findAll() {
		return List.of(
			QuestRewardPolicy.of(QuestType.DAILY, QuestReward.of(ScoreReward.of(2), PointReward.of(10))),
			QuestRewardPolicy.of(QuestType.WEEKLY, QuestReward.of(ScoreReward.of(20), PointReward.of(150))),
			QuestRewardPolicy.of(QuestType.MONTHLY, QuestReward.of(ScoreReward.of(100), PointReward.of(1500)))
		);
	}
}
