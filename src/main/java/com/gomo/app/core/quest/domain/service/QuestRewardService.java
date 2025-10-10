package com.gomo.app.core.quest.domain.service;

import com.gomo.app.common.arch.DomainService;
import com.gomo.app.core.quest.domain.model.reward.PointReward;
import com.gomo.app.core.quest.domain.model.reward.QuestReward;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.reward.ScoreReward;
import com.gomo.app.core.quest.domain.model.assign.AssignQuestId;
import com.gomo.app.core.quest.domain.repository.QuestRewardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class QuestRewardService {

	private final QuestRewardRepository questRewardRepository;

	public QuestReward create(AssignQuestId assignQuestId, QuestType questType) {
		ScoreReward scoreReward = questRewardRepository.findScoreReward(questType);
		PointReward pointReward = questRewardRepository.findPointReward(questType);
		return QuestReward.of(assignQuestId, scoreReward, pointReward);
	}
}
