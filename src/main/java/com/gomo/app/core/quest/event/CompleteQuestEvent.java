package com.gomo.app.core.quest.event;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.common.event.Event;
import com.gomo.app.common.event.EventRouting;

import lombok.Getter;

@EventRouting(exchange = "quest", routingKey = "quest.event.completed")
@Getter
public class CompleteQuestEvent extends Event {

	// todo [Architecture] jhl221123: 이벤트는 primitive type을 사용한다. 단, 타입이 필요한 상황이 발생하면 공개 언어 VO로 교체한다.
	private final UUID participantId;
	private final UUID subjectId;
	private final String questType;
	private final int scoreReward;
	private final int pointReward;
	private final LocalDateTime questCompletionTime;

	private CompleteQuestEvent(UUID participantId, UUID subjectId, String questType, int scoreReward, int pointReward, LocalDateTime questCompletionTime,
		long timestamp) {
		super(timestamp);
		this.participantId = participantId;
		this.subjectId = subjectId;
		this.questType = questType;
		this.scoreReward = scoreReward;
		this.pointReward = pointReward;
		this.questCompletionTime = questCompletionTime;
	}

	public static CompleteQuestEvent of(UUID participantId, UUID subjectId, String questType, int scoreReward, int pointReward, LocalDateTime questCompletionTime,
		long timestamp) {
		return new CompleteQuestEvent(
			participantId,
			subjectId,
			questType,
			scoreReward,
			pointReward,
			questCompletionTime,
			timestamp
		);
	}
}
