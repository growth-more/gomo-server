package com.gomo.app.core.quest.fixture;

import com.gomo.app.core.quest.domain.model.reward.PointReward;
import com.gomo.app.core.quest.domain.model.reward.QuestReward;
import com.gomo.app.core.quest.domain.model.reward.ScoreReward;

public class QuestRewardFixture {

	public static QuestReward create() {
		return QuestReward.of(
			ScoreReward.of(2),
			PointReward.of(10)
		);
	}

	public static QuestReward create(int score, int points) {
		return QuestReward.of(
			ScoreReward.of(score),
			PointReward.of(points)
		);
	}
}
