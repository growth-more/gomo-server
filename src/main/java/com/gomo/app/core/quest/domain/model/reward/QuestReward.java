package com.gomo.app.core.quest.domain.model.reward;

import com.gomo.app.common.arch.ValueObject;
import com.gomo.app.core.quest.domain.model.assign.AssignQuestId;

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

	protected QuestReward() {
	}

	private QuestReward(AssignQuestId assignQuestId, ScoreReward scoreReward, PointReward pointReward) {
		this.assignQuestId = assignQuestId;
		this.scoreReward = scoreReward;
		this.pointReward = pointReward;
	}

	public static QuestReward of(AssignQuestId assignQuestId, ScoreReward scoreReward, PointReward pointReward) {
		return new QuestReward(assignQuestId, scoreReward, pointReward);
	}

	public int scoreReward() {
		return this.scoreReward.getScore();
	}

	public int pointReward() {
		return this.pointReward.getAmount();
	}
}
