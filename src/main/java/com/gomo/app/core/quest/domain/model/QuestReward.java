package com.gomo.app.core.quest.domain.model;

import com.gomo.app.common.arch.ValueObject;

import jakarta.persistence.Embedded;
import lombok.Getter;

@Getter
@ValueObject
public class QuestReward {

	@Embedded
	private AssignQuestId assignQuestId;

	@Embedded
	private ScoreReward scoreReward;

	@Embedded
	private PointReward pointReward;

	protected QuestReward() {}

	private QuestReward(
		AssignQuestId assignQuestId,
		ScoreReward scoreReward,
		PointReward pointReward
	) {
		this.assignQuestId = assignQuestId;
		this.scoreReward = scoreReward;
		this.pointReward = pointReward;
	}

	public static QuestReward of(
		AssignQuestId assignQuestId,
		ScoreReward scoreReward,
		PointReward pointReward
	) {
		return new QuestReward(assignQuestId, scoreReward, pointReward);
	}
}
