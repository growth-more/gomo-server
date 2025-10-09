package com.gomo.app.support.evententry.application.port;

public interface MessageBrokerPortOut {

	/**
	 * Publishes a message to a specific exchange with a given routing key.
	 *
	 * @param exchange   The name of the exchange to which the message will be published.
	 * @param routingKey The routing key to use for the message.
	 * @param message    The message payload to be sent, typically a serialized event.
	 * @throws IllegalStateException if the message fails to be sent due to a connection issue
	 *         or other broker-related problems.
	 */
	void send(String exchange, String routingKey, String message);
}
