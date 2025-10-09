package com.gomo.app.support.evententry.application.port;

public interface ForwardEventEntryPortIn {

	/**
	 * Reads a batch of unprocessed event entries from the persistent store and forwards them
	 * to the message broker.
	 *
	 * @param size The maximum number of event entries to process and forward in a single execution batch.
	 */
	void execute(int size);
}
