package com.gomo.app.quest.event;

import java.util.UUID;

import com.gomo.app.common.event.Event;
import com.gomo.app.quest.domain.model.ScoreReward;
import com.gomo.app.quest.domain.model.SubjectId;

import lombok.Getter;

@Getter
public class ScoreQuestCompletedEvent extends Event {

	private UUID memberId;
	private SubjectId subjectId;
	private ScoreReward scoreReward;

	private ScoreQuestCompletedEvent(
		UUID memberId,
		SubjectId subjectId,
		ScoreReward scoreReward
	) {
		this.memberId = memberId;
		this.subjectId = subjectId;
		this.scoreReward = scoreReward;
	}

	public static ScoreQuestCompletedEvent of(
		UUID memberId,
		SubjectId subjectId,
		ScoreReward scoreReward
	) {
		return new ScoreQuestCompletedEvent(memberId, subjectId, scoreReward);
	}
}
