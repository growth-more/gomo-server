package com.gomo.app.support.event.application.port;

public interface MessageBrokerPortOut {

	void send(String exchange, String routingKey, String message);
}
