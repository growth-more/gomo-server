package com.gomo.app.test.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RabbitMQConfig {

	public static final String TEST_EXCHANGE = "test.exchange";
	public static final String TEST_ROUTING_KEY = "test.key";
	public static final String TEST_QUEUE = "test.queue";

	@Bean
	public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	public Queue testQueue() {
		return new Queue(TEST_QUEUE, false);
	}

	@Bean
	public DirectExchange testExchange() {
		return new DirectExchange(TEST_EXCHANGE);
	}

	@Bean
	public Binding testBinding(Queue testQueue, DirectExchange testExchange) {
		return BindingBuilder.bind(testQueue).to(testExchange).with(TEST_ROUTING_KEY);
	}
}
