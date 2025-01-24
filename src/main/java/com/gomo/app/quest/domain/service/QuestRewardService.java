package com.gomo.app.quest.domain.service;

import java.util.List;

import com.gomo.app.common.domain.DomainService;
import com.gomo.app.quest.domain.model.QuestPointPolicy;
import com.gomo.app.quest.domain.model.QuestReward;
import com.gomo.app.quest.domain.model.QuestScorePolicy;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.repository.QuestRewardPolicyRepository;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class QuestRewardService {

	private final QuestRewardPolicyRepository questRewardPolicyRepository;
	private final List<QuestScorePolicy> questScorePolicyCache;
	private final List<QuestPointPolicy> questPointPolicyCache;

	protected void initializeCaches() {}

	public QuestReward create(QuestType questType) {
		return null;
	}
}
