package com.gomo.app.core.quest.domain.repository;

import java.util.List;

import com.gomo.app.core.quest.domain.model.reward.QuestRewardPolicy;

public interface QuestRewardPolicyRepository {

	List<QuestRewardPolicy> findAll();
}
