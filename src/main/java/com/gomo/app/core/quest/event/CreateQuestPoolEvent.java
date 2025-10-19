package com.gomo.app.core.quest.event;

import java.util.List;
import java.util.UUID;

import com.gomo.app.common.event.Event;
import com.gomo.app.common.event.EventRouting;
import com.gomo.app.core.quest.domain.model.quest.QuestType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EventRouting(exchange = "event.quest", routingKey = "event.quest.pool.fill")
public class CreateQuestPoolEvent extends Event {

	/**
	 * The unique identifier of the participant targeted for filling quest pools.
	 */
	private UUID participantId;

	/**
	 * The participant's subjects, used for generating quest pool content.
	 */
	private List<Subject> subjects;

	/**
	 * The type of the completed quest.
	 * Corresponds to the names in the {@link QuestType} enum (e.g., "DAILY", "WEEKLY").
	 */
	private String questType;

	/**
	 * The maximum capacity of the quest pool.
	 */
	private long limit;

	private CreateQuestPoolEvent(UUID participantId, List<Subject> subjects, String questType, long limit, long timestamp) {
		super(timestamp);
		this.participantId = participantId;
		this.subjects = subjects;
		this.questType = questType;
		this.limit = limit;
	}

	public static CreateQuestPoolEvent of(UUID participantId, List<Subject> subjects, String questType, long limit, long timestamp) {
		return new CreateQuestPoolEvent(participantId, subjects, questType, limit, timestamp);
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Subject {

		/**
		 * The unique identifier for the subject of the quest.
		 */
		private UUID id;

		/**
		 * The name of the subject for the quest.
		 */
		private String name;

		/**
		 * The proficiency of subject. Quest difficulty is generated based on this level.
		 */
		private int level;

		private Subject(UUID id, String name, int level) {
			this.id = id;
			this.name = name;
			this.level = level;
		}

		public static Subject of(UUID id, String name, int level) {
			return new Subject(id, name, level);
		}
	}
}
