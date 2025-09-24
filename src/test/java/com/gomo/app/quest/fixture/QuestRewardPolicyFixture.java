package com.gomo.app.quest.fixture;

import java.util.List;

import com.gomo.app.core.quest.domain.model.QuestPointPolicy;
import com.gomo.app.core.quest.domain.model.QuestScorePolicy;
import com.gomo.app.core.quest.domain.model.QuestType;

public class QuestRewardPolicyFixture {

	public static List<QuestPointPolicy> point() {
		return List.of(
			QuestPointPolicy.of(QuestType.DAILY, 10),
			QuestPointPolicy.of(QuestType.WEEKLY, 150),
			QuestPointPolicy.of(QuestType.MONTHLY, 1500)
		);
	}

	public static List<QuestScorePolicy> score() {
		return List.of(
			QuestScorePolicy.of(QuestType.DAILY, 2),
			QuestScorePolicy.of(QuestType.WEEKLY, 20),
			QuestScorePolicy.of(QuestType.MONTHLY, 100)
		);
	}
}
