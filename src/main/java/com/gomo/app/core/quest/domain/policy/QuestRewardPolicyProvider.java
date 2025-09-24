package com.gomo.app.core.quest.domain.policy;

import com.gomo.app.core.quest.domain.model.PointReward;
import com.gomo.app.core.quest.domain.model.QuestType;
import com.gomo.app.core.quest.domain.model.ScoreReward;

public interface QuestRewardPolicyProvider {

	ScoreReward getScoreReward(QuestType questType);

	PointReward getPointReward(QuestType questType);
}
