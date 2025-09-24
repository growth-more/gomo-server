package com.gomo.app.core.quest.domain.repository;

import java.util.List;

import com.gomo.app.core.quest.domain.model.QuestPointPolicy;
import com.gomo.app.core.quest.domain.model.QuestScorePolicy;

public interface QuestRewardPolicyRepository {

	List<QuestScorePolicy> findScorePolicies();

	List<QuestPointPolicy> findPointPolicies();
}
