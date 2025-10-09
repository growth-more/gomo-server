package com.gomo.app.support.evententry.infrastructure.adapter;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.support.evententry.application.port.MessageBrokerPortOut;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Adapter
class RabbitMQAdapter implements MessageBrokerPortOut {

	private final RabbitTemplate rabbitTemplate;

	@Override
	public void send(String exchange, String routingKey, String message) {
		log.info("Sending message: '{}' to exchange: '{}', routingKey: '{}'", message, exchange, routingKey);
		Boolean success = rabbitTemplate.invoke(operations -> {
			operations.convertAndSend(exchange, routingKey, message);
			return operations.waitForConfirms(5000);
		});

		if (success == null || !success) {
			log.error("Failed to send message: '{}' to exchange: '{}', routingKey: '{}'", message, exchange, routingKey);
			throw new IllegalStateException("Failed to send message");
		}
	}
}
