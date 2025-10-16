package com.gomo.app.core.quest.event;

import java.util.List;
import java.util.UUID;

import com.gomo.app.common.event.Event;
import com.gomo.app.common.event.EventRouting;

import lombok.Getter;

@EventRouting(exchange = "event.quest", routingKey = "event.quest.pool.fill")
@Getter
public class FillQuestPoolEvent extends Event {

	/**
	 * The unique identifier of the participant targeted for filling quest pools.
	 */
	private final UUID participantId;

	/**
	 * The participant's subjects, used for generating quest pool content.
	 */
	private final List<Subject> subjects;

	/**
	 * The maximum capacity of the quest pool.
	 */
	private final long limit;

	private FillQuestPoolEvent(UUID participantId, List<Subject> subjects, long limit, long timestamp) {
		super(timestamp);
		this.participantId = participantId;
		this.limit = limit;
		this.subjects = subjects;
	}

	public static FillQuestPoolEvent of(UUID participantId, List<Subject> subjects, long limit, long timestamp) {
		return new FillQuestPoolEvent(participantId, subjects, limit, timestamp);
	}

	public static class Subject {

		/**
		 * The unique identifier for the subject of the quest.
		 */
		private final UUID subjectId;

		/**
		 * The name of the subject for the quest.
		 */
		private final String subjectName;

		/**
		 * The proficiency of subject. Quest difficulty is generated based on this level.
		 */
		private final int level;

		private Subject(UUID subjectId, String subjectName, int level) {
			this.subjectId = subjectId;
			this.subjectName = subjectName;
			this.level = level;
		}

		public static Subject of(UUID subjectId, String subjectName, int level) {
			return new Subject(subjectId, subjectName, level);
		}
	}
}
