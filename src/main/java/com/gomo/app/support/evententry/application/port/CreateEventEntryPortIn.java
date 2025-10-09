package com.gomo.app.support.evententry.application.port;

public interface CreateEventEntryPortIn {

	/**
	 * Persists a new event to the database.
	 *
	 * @param eventName A string identifying the type of the event (e.g., "CompleteQuestEvent").
	 * @param payload   The main data of the event, typically in a serialized format like JSON.
	 * @param timestamp The Unix timestamp (milliseconds since epoch) indicating when the event occurred.
	 * @return The id of the newly created and persisted event entry.
	 */
	Long create(String eventName, String payload, long timestamp);
}
