package com.gomo.app.quest.infrastructure;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gomo.app.quest.domain.model.QuestPointPolicy;
import com.gomo.app.quest.domain.model.QuestScorePolicy;
import com.gomo.app.quest.domain.repository.QuestRewardPolicyRepository;

@Repository
public class QuestRewardPolicyRepositoryImpl implements QuestRewardPolicyRepository {

	@Override
	public List<QuestScorePolicy> findScorePolicies() {
		return List.of();
	}

	@Override
	public List<QuestPointPolicy> findPointPolicies() {
		return List.of();
	}
}
