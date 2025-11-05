package com.gomo.app.common.event;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.core.quest.domain.model.quest.QuestType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EventRouting(exchange = "event.quest", routingKey = "event.quest.assign.complete")
public class CompleteQuestEvent extends Event {
	// todo [Architecture] jhl221123: 이벤트는 primitive type을 사용한다. 단, 타입이 필요한 상황이 발생하면 공개 언어 VO로 교체한다.

	/**
	 * The unique identifier of the participant who completed the quest.
	 */
	private UUID participantId;

	/**
	 * The unique identifier for the subject of the quest.
	 */
	private UUID subjectId;

	/**
	 * The type of the completed quest.
	 * Corresponds to the names in the {@link QuestType} enum (e.g., "DAILY", "WEEKLY").
	 */
	private String questType;

	/**
	 * The score reward, a fixed value determined by the {@code questType}.
	 */
	private int scoreReward;

	/**
	 * The point reward, a fixed value determined by the {@code questType}.
	 */
	private int pointReward;

	/**
	 * The exact date and time when the quest was marked as complete.
	 */
	private LocalDateTime questCompletionTime;

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
