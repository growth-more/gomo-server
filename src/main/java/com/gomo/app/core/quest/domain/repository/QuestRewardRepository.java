package com.gomo.app.core.quest.domain.repository;

import com.gomo.app.core.quest.domain.model.reward.PointReward;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.reward.ScoreReward;

public interface QuestRewardRepository {

	ScoreReward findScoreReward(QuestType questType);

	PointReward findPointReward(QuestType questType);
}
