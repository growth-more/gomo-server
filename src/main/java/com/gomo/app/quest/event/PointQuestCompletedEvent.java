package com.gomo.app.quest.event;

import com.gomo.app.common.event.Event;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.PointReward;

import lombok.Getter;

@Getter
public class PointQuestCompletedEvent extends Event {

	private ParticipantId participantId;
	private PointReward pointReward;

	private PointQuestCompletedEvent(
		ParticipantId participantId,
		PointReward pointReward
	) {
		this.participantId = participantId;
		this.pointReward = pointReward;
	}

	public static PointQuestCompletedEvent of (
		ParticipantId participantId,
		PointReward pointReward
	) {
		return new PointQuestCompletedEvent(participantId, pointReward);
	}
}
