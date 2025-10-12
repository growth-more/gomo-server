package com.gomo.app.core.quest.domain.model.reward;

import com.gomo.app.common.arch.ValueObject;
import com.gomo.app.core.quest.domain.model.quest.QuestType;

@ValueObject
public record QuestRewardPolicy(QuestType questType, QuestReward questReward) {

	public static QuestRewardPolicy of(QuestType questType, QuestReward questReward) {
		return new QuestRewardPolicy(questType, questReward);
	}
}
