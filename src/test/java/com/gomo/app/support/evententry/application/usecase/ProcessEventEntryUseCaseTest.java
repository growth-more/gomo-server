package com.gomo.app.support.evententry.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.support.evententry.domain.repository.ProcessedEventEntryRepository;

@DisplayName("[Application Unit]: EventEntry 처리 테스트")
@ExtendWith(MockitoExtension.class)
class ProcessEventEntryUseCaseTest {

	@InjectMocks
	private ProcessEventEntryUseCase sut;

	@Mock
	private ProcessedEventEntryRepository processedEventEntryRepository;

	@DisplayName("처리한 EventEntry를 저장한다.")
	@Test
	void process_event_entry() {
		sut.process("123", "ConsumerClassName");
		verify(processedEventEntryRepository, times(1)).save(anyString(), anyString());
	}

	@DisplayName("이미 처리된 EventEntry인지 확인한다.")
	@Test
	void is_already_processed() {
		doReturn(true).when(processedEventEntryRepository).existsByEventEntryIdAndConsumerName(anyString(), anyString());
		boolean actual = sut.isAlreadyProcessed("123", "ConsumerClassName");
		assertThat(actual).isTrue();
	}
}
