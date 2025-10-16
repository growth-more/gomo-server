package com.gomo.app.support.messagebroker.application.port;

public interface PublishMessagePortIn {

	/**
	 * Publishes a message to a specific destination with a given key.
	 *
	 * @param destination   The name of the destination(e.g. exchange or topic) to which the message will be published.
	 * @param key            The routing key to use for the message.
	 * @param message        The message payload to be sent, typically a serialized event.
	 * @throws IllegalStateException if the message fails to be sent due to a connection issue
	 *         or other broker-related problems.
	 */
	void send(String destination, String key, String message);
}
