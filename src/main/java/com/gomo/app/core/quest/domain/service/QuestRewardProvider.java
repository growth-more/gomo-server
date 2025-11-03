package com.gomo.app.core.quest.domain.service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.gomo.app.common.arch.DomainService;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.reward.QuestReward;
import com.gomo.app.core.quest.domain.model.reward.QuestRewardPolicy;
import com.gomo.app.core.quest.domain.repository.QuestRewardPolicyRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class QuestRewardProvider {

	private Map<QuestType, QuestRewardPolicy> rewardMap;
	private final QuestRewardPolicyRepository questRewardPolicyRepository;

	@PostConstruct
	protected void initializeCaches() {
		rewardMap = questRewardPolicyRepository.findAll().stream()
			.collect(Collectors.toMap(QuestRewardPolicy::questType, Function.identity()));
	}

	public QuestReward provide(QuestType questType) {
		if (rewardMap.containsKey(questType)) {
			return rewardMap.get(questType).questReward();
		}
		throw new IllegalArgumentException("Unknown QuestType: " + questType);
	}
}
