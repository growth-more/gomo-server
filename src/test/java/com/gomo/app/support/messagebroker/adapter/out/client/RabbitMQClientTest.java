package com.gomo.app.support.messagebroker.adapter.out.client;

import static com.gomo.app.test.config.RabbitMQConfig.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.test.IntegrationTest;
import com.gomo.app.test.WithRabbitMQ;
import com.rabbitmq.client.ShutdownSignalException;

@DisplayName("[Infrastructure Integration]: RabbitMQ 접근 테스트 (Flaky Test)")
@IntegrationTest
@WithRabbitMQ
class RabbitMQClientTest {

	@Autowired
	private RabbitMQClient rabbitMQClient;

	@Autowired
	protected RabbitTemplate rabbitTemplate;

	@DisplayName("메시지를 지정된 exchange로 보낸다")
	@Test
	void send_message() {
		String message = "{\"status\":\"success\"}";

		assertDoesNotThrow(() -> rabbitMQClient.send(TEST_EXCHANGE, TEST_ROUTING_KEY, message));

		Object receivedMessage = rabbitTemplate.receiveAndConvert(TEST_QUEUE, 2000);
		assertThat(receivedMessage).isNotNull();
		assertThat(receivedMessage.toString()).isEqualTo(message);
	}

	@DisplayName("존재하지 않는 exchange로 메시지를 보낸다")
	@Test
	void send_message_nonexistent_exchange() {
		// TODO [2025-10-09] jhl221123 : Flaky Test. AmqpException 이 발생할 수도 있습니다. 추후 정확한 원인 파악 필요합니다.
		assertThrows(ShutdownSignalException.class, () -> rabbitMQClient.send("non.existent.exchange", TEST_ROUTING_KEY, "This will fail"));
	}
}
