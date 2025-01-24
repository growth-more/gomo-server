package com.gomo.app.quest.domain.repository;

import java.util.List;

import com.gomo.app.quest.domain.model.QuestPointPolicy;
import com.gomo.app.quest.domain.model.QuestScorePolicy;

public interface QuestRewardPolicyRepository {

	List<QuestScorePolicy> findScorePolicies();

	List<QuestPointPolicy> findPointPolicies();
}
