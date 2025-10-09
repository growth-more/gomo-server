package com.gomo.app.test.extenstion;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gomo.app.test.config.RabbitMQConfig;

public class RabbitMQCleanUpExtension implements BeforeEachCallback {

	@Override
	public void beforeEach(ExtensionContext context) {
		ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
		AmqpAdmin amqpAdmin = applicationContext.getBean(AmqpAdmin.class);

		String queueName = RabbitMQConfig.TEST_QUEUE;
		amqpAdmin.purgeQueue(queueName);

		System.out.println("RabbitMQ queue '" + queueName + "' purged before test execution.");
	}
}
