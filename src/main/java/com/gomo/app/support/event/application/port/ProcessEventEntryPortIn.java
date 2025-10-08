package com.gomo.app.support.event.application.port;

public interface ProcessEventEntryPortIn {

	/**
	 * Saves a record indicating that an event has been processed by a consumer.
	 *
	 * @param eventEntryId The id of the event entry.
	 * @param consumerName The class name of the consumer that processed the event
	 *                     (e.g., {@code MyConsumer.class.getName()}).
	 */
	void process(String eventEntryId, String consumerName);

	/**
	 * Checks if an event has already been processed by a specific consumer.
	 *
	 * @param eventEntryId The id of the event entry.
	 * @param consumerName The class name of the consumer to check against.
	 * @return {@code true} if the event has already been processed, {@code false} otherwise.
	 */
	boolean isAlreadyProcessed(String eventEntryId, String consumerName);
}
