package com.gomo.app.core.quest.event;

import com.gomo.app.common.event.Event;
import com.gomo.app.core.quest.domain.model.ParticipantId;
import com.gomo.app.core.quest.domain.model.PointReward;

import lombok.Getter;

@Getter
public class PointQuestCompletedEvent extends Event {

	private ParticipantId participantId;
	private PointReward pointReward;

	private PointQuestCompletedEvent() {
		super();
	}

	private PointQuestCompletedEvent(
		ParticipantId participantId,
		PointReward pointReward,
		long timestamp
	) {
		super(timestamp);
		this.participantId = participantId;
		this.pointReward = pointReward;
	}

	public static PointQuestCompletedEvent of(
		ParticipantId participantId,
		PointReward pointReward,
		long timestamp
	) {
		return new PointQuestCompletedEvent(participantId, pointReward, timestamp);
	}
}
