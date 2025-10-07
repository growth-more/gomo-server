package com.gomo.app.core.quest.domain.repository;

import java.util.List;

import com.gomo.app.core.quest.domain.model.reward.policy.QuestPointPolicy;
import com.gomo.app.core.quest.domain.model.reward.policy.QuestScorePolicy;

public interface QuestRewardPolicyRepository {

	List<QuestScorePolicy> findScorePolicies();

	List<QuestPointPolicy> findPointPolicies();
}
