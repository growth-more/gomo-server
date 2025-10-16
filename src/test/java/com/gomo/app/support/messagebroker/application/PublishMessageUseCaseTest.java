package com.gomo.app.support.messagebroker.application;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.support.messagebroker.application.port.MessageBrokerClientPortOut;

@DisplayName("[Application Unit]: 메시지 발행 테스트")
@ExtendWith(MockitoExtension.class)
class PublishMessageUseCaseTest {

	@InjectMocks
	private PublishMessageUseCase sut;

	@Mock
	private MessageBrokerClientPortOut messageBrokerClientPortOut;

	@DisplayName("이벤트를 발행한다.")
	@Test
	void process_event_entry() {
		sut.send("destination", "key", "message");
		verify(messageBrokerClientPortOut, times(1)).send(anyString(), anyString(), anyString());
	}
}
