package com.gomo.app.quest.domain.policy;

import com.gomo.app.quest.domain.model.PointReward;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.ScoreReward;

public interface QuestRewardPolicyProvider {

	ScoreReward getScoreReward(QuestType questType);

	PointReward getPointReward(QuestType questType);
}
