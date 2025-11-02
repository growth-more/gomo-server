package com.gomo.app.support.messagebroker.application.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.support.messagebroker.application.port.out.MessageBrokerManager;

@DisplayName("[Application Unit]: 메시지 발행 테스트")
@ExtendWith(MockitoExtension.class)
class MessagePublishServiceTest {

	@InjectMocks
	private MessagePublishService sut;

	@Mock
	private MessageBrokerManager messageBrokerManager;

	@DisplayName("이벤트를 발행한다.")
	@Test
	void process_event_entry() {
		sut.send("destination", "key", "message");
		verify(messageBrokerManager, times(1)).send(anyString(), anyString(), anyString());
	}
}
