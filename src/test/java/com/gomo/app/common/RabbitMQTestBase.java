package com.gomo.app.common;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import com.gomo.app.common.config.RabbitMQConfig;

@Import(RabbitMQConfig.class)
public abstract class RabbitMQTestBase extends IntegrationTestBase {

	protected static final String TEST_EXCHANGE = RabbitMQConfig.TEST_EXCHANGE;
	protected static final String TEST_ROUTING_KEY = RabbitMQConfig.TEST_ROUTING_KEY;
	protected static final String TEST_QUEUE = RabbitMQConfig.TEST_QUEUE;

	@Autowired
	protected AmqpAdmin amqpAdmin;

	@Autowired
	protected RabbitTemplate rabbitTemplate;
}
