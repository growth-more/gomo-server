package com.gomo.app.support.evententry.infrastructure.aspect;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.gomo.app.support.evententry.application.port.IdempotentEventEntryConsumer;
import com.gomo.app.support.evententry.application.port.ProcessEventEntryPortIn;
import com.gomo.app.support.evententry.domain.model.EventEntry;
import com.gomo.app.test.IntegrationTest;

@DisplayName("[Aspect integration]: 멱등성 컨슈머 AOP 테스트")
@IntegrationTest
@Import({IdempotentEventEntryConsumerAspectTest.FakeConsumer.class, IdempotentEventEntryConsumerAspectTest.MockBusinessLogic.class})
@Transactional
class IdempotentEventEntryConsumerAspectTest {

	@Autowired
	private FakeConsumer sut;

	@MockitoSpyBean
	private MockBusinessLogic mockBusinessLogic;

	@Autowired
	private ProcessEventEntryPortIn processEventEntryPortIn;

	@Autowired
	private PlatformTransactionManager transactionManager;

	private TransactionTemplate transactionTemplate;

	@BeforeEach
	void setUp() {
		Mockito.reset(mockBusinessLogic);
		transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
	}

	@DisplayName("최초 이벤트는 비즈니스 로직이 실행되고, 처리 기록이 저장된다.")
	@Test
	void process_event_entry() {
		EventEntry eventEntry = EventEntry.of("TestEvent", "payload", 1L);

		sut.testHandleEvent(eventEntry);

		verify(mockBusinessLogic, times(1)).doSomething();
		boolean isProcessed = processEventEntryPortIn.isAlreadyProcessed(String.valueOf(eventEntry.getId()), FakeConsumer.class.getName());
		assertThat(isProcessed).isTrue();
	}

	@DisplayName("중복된 이벤트는 비즈니스 로직이 실행되지 않는다")
	@Test
	void skip_duplicate_event_entry() {
		EventEntry eventEntry = EventEntry.of("TestEvent", "payload", 2L);
		processEventEntryPortIn.process(String.valueOf(eventEntry.getId()), FakeConsumer.class.getName());

		sut.testHandleEvent(eventEntry);

		verify(mockBusinessLogic, times(0)).doSomething();
	}

	@DisplayName("비즈니스 로직 실패 시, 이벤트 처리 기록이 롤백되어야 한다")
	@Test
	void shouldRollbackWhenBusinessLogicFails() {
		EventEntry eventEntry = EventEntry.of("TestEvent", "payload", 3L);
		doThrow(new IllegalStateException("Business logic failure")).when(mockBusinessLogic).doSomething();

		assertThrows(IllegalStateException.class, () -> {
			transactionTemplate.execute(status -> {
				sut.testHandleEvent(eventEntry);
				return null;
			});
		});

		boolean isProcessed = processEventEntryPortIn.isAlreadyProcessed(String.valueOf(eventEntry.getId()), FakeConsumer.class.getName());
		assertThat(isProcessed).isFalse();
	}

	@Component
	static class FakeConsumer {

		@Autowired
		private MockBusinessLogic mockBusinessLogic;

		@IdempotentEventEntryConsumer
		public void testHandleEvent(EventEntry eventEntry) {
			mockBusinessLogic.doSomething();
		}
	}

	@Component
	static class MockBusinessLogic {
		void doSomething() {
		}
	}
}
