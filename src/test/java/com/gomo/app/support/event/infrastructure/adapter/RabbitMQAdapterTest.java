package com.gomo.app.support.event.infrastructure.adapter;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.RabbitMQTestBase;
import com.gomo.app.support.event.application.port.MessageBrokerPortOut;
import com.rabbitmq.client.ShutdownSignalException;

@DisplayName("[Infrastructure Integration]: RabbitMQ 접근 테스트")
class RabbitMQAdapterTest extends RabbitMQTestBase {

	@Autowired
	private MessageBrokerPortOut messageBrokerPortOut;

	@DisplayName("메시지를 지정된 exchange로 보낸다")
	@Test
	void send_message() {
		String message = "{\"status\":\"success\"}";

		assertDoesNotThrow(() -> messageBrokerPortOut.send(TEST_EXCHANGE, TEST_ROUTING_KEY, message));

		Object receivedMessage = rabbitTemplate.receiveAndConvert(TEST_QUEUE, 2000);
		assertThat(receivedMessage).isNotNull();
		assertThat(receivedMessage.toString()).isEqualTo(message);
	}

	@DisplayName("존재하지 않는 exchange로 메시지를 보낸다")
	@Test
	void send_message_nonexistent_exchange() {
		assertThrows(ShutdownSignalException.class, () -> messageBrokerPortOut.send("non.existent.exchange", TEST_ROUTING_KEY, "This will fail"));
	}
}
