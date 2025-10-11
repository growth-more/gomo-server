package com.gomo.app.core.quest.domain.model.reward;

import java.util.UUID;

import com.gomo.app.common.arch.ValueObject;

import jakarta.persistence.Embedded;
import lombok.Getter;

@Getter
@ValueObject
public class QuestReward {

	@Embedded
	private UUID assignQuestId;

	@Embedded
	private ScoreReward scoreReward;

	@Embedded
	private PointReward pointReward;

	protected QuestReward() {
	}

	private QuestReward(UUID assignQuestId, ScoreReward scoreReward, PointReward pointReward) {
		this.assignQuestId = assignQuestId;
		this.scoreReward = scoreReward;
		this.pointReward = pointReward;
	}

	public static QuestReward of(UUID assignQuestId, ScoreReward scoreReward, PointReward pointReward) {
		return new QuestReward(assignQuestId, scoreReward, pointReward);
	}

	public int scoreReward() {
		return this.scoreReward.getScore();
	}

	public int pointReward() {
		return this.pointReward.getAmount();
	}
}
