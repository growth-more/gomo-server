package com.gomo.app.test.container;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	public static final RabbitMQContainer rabbitMQContainer;

	static {
		rabbitMQContainer = new RabbitMQContainer("rabbitmq:3.13-management")
			.withExposedPorts(5672)
			.withReuse(true)
			.waitingFor(Wait.forListeningPort());
		rabbitMQContainer.start();
		declareQueueOfRabbitMQ();
	}

	@Override
	public void initialize(ConfigurableApplicationContext context) {
		TestPropertyValues.of(
			"spring.rabbitmq.host=" + rabbitMQContainer.getHost(),
			"spring.rabbitmq.port=" + rabbitMQContainer.getMappedPort(5672),
			"spring.rabbitmq.username=" + rabbitMQContainer.getAdminUsername(),
			"spring.rabbitmq.password=" + rabbitMQContainer.getAdminPassword(),
			"spring.rabbitmq.publisher-confirm-type=correlated",
			"spring.rabbitmq.publisher-returns=true",
			"spring.rabbitmq.template.mandatory=true"
		).applyTo(context.getEnvironment());
	}

	private static void declareQueueOfRabbitMQ() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(rabbitMQContainer.getHost());
			factory.setPort(rabbitMQContainer.getAmqpPort());
			factory.setUsername(rabbitMQContainer.getAdminUsername());
			factory.setPassword(rabbitMQContainer.getAdminPassword());

			try (Connection connection = factory.newConnection();
				 Channel channel = connection.createChannel()) {
				// durable: true, exclusive: false, autoDelete: false, arguments: null
				channel.queueDeclare("event.quest.complete.score", true, false, false, null);
				channel.queueDeclare("event.quest.complete.streak", true, false, false, null);
				channel.queueDeclare("event.quest.complete.point", true, false, false, null);
				channel.queueDeclare("event.quest.complete.point", true, false, false, null);
			}
		} catch (IOException | TimeoutException e) {
			throw new RuntimeException("Failed to declare RabbitMQ queue for testing", e);
		}
	}
}
