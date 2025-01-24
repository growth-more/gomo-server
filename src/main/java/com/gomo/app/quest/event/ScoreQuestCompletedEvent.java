package com.gomo.app.quest.event;

import com.gomo.app.common.event.Event;
import com.gomo.app.quest.domain.model.ScoreReward;
import com.gomo.app.quest.domain.model.SubjectId;

import lombok.Getter;

@Getter
public class ScoreQuestCompletedEvent extends Event {

	private SubjectId subjectId;
	private ScoreReward scoreReward;

	private ScoreQuestCompletedEvent(
		SubjectId subjectId,
		ScoreReward scoreReward
	) {
		this.subjectId = subjectId;
		this.scoreReward = scoreReward;
	}

	public static ScoreQuestCompletedEvent of(
		SubjectId subjectId,
		ScoreReward scoreReward
	) {
		return new ScoreQuestCompletedEvent(subjectId, scoreReward);
	}
}
