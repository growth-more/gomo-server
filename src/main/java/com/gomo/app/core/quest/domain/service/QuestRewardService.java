package com.gomo.app.core.quest.domain.service;

import com.gomo.app.common.arch.DomainService;
import com.gomo.app.core.quest.domain.model.AssignQuestId;
import com.gomo.app.core.quest.domain.model.PointReward;
import com.gomo.app.core.quest.domain.model.QuestReward;
import com.gomo.app.core.quest.domain.model.QuestType;
import com.gomo.app.core.quest.domain.model.ScoreReward;
import com.gomo.app.core.quest.domain.policy.QuestRewardPolicyProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class QuestRewardService {

	private final QuestRewardPolicyProvider questRewardPolicyProvider;

	public QuestReward create(AssignQuestId assignQuestId, QuestType questType) {
		ScoreReward scoreReward = questRewardPolicyProvider.getScoreReward(questType);
		PointReward pointReward = questRewardPolicyProvider.getPointReward(questType);
		return QuestReward.of(assignQuestId, scoreReward, pointReward);
	}
}
