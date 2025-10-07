package com.gomo.app.core.interest.presentation.consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import com.gomo.app.common.util.JsonParser;
import com.gomo.app.core.interest.application.port.AdjustProficiencyPortIn;
import com.gomo.app.core.quest.event.CompleteQuestEvent;
import com.gomo.app.support.event.application.port.ProcessEventEntryPortIn;
import com.gomo.app.support.event.domain.model.EventEntry;

@DisplayName("[Consumer unit]: 퀘스트 완료(점수) 이벤트 처리 테스트")
@ExtendWith(MockitoExtension.class)
class CompleteQuestEventConsumerTest {

	@InjectMocks
	private CompleteQuestEventConsumer sut;

	@Mock
	private ProcessEventEntryPortIn processEventEntryPortIn;

	@Mock
	private AdjustProficiencyPortIn adjustProficiencyPortIn;

	@DisplayName("숙련도 향상 이벤트를 처리한다.")
	@Test
	void event_process() {
		EventEntry eventEntry = EventEntry.of("CompleteQuestEvent", "payload", 1L);
		CompleteQuestEvent event = CompleteQuestEvent.of(UUID.randomUUID(), UUID.randomUUID(), "DAILY", 2, 10, LocalDateTime.now(), 1L);
		doReturn(false).when(processEventEntryPortIn).isAlreadyProcessed(anyString(), anyString());

		try (MockedStatic<JsonParser> mockedStatic = mockStatic(JsonParser.class)) {
			mockedStatic.when(() -> JsonParser.fromJson("payload", CompleteQuestEvent.class)).thenReturn(event);

			sut.handleEvent(eventEntry);

			verify(processEventEntryPortIn, times(1)).save(anyString(), anyString());
			verify(adjustProficiencyPortIn, times(1)).adjust(any(), anyInt());
		}
	}

	@DisplayName("이미 처리된 이벤트는 처리되지 않는다.")
	@Test
	void do_not_process_duplicated_event() {
		EventEntry eventEntry = EventEntry.of("CompleteQuestEvent", "payload", 1L);
		doReturn(true).when(processEventEntryPortIn).isAlreadyProcessed(anyString(), anyString());

		sut.handleEvent(eventEntry);

		verifyNoInteractions(adjustProficiencyPortIn);
	}

	@DisplayName("숙련도 향상 작업에 실패한다.")
	@Test
	void cannot_process_event_by_score() {
		EventEntry eventEntry = EventEntry.of("CompleteQuestEvent", "payload", 1L);
		CompleteQuestEvent event = CompleteQuestEvent.of(UUID.randomUUID(), UUID.randomUUID(), "DAILY", 2, 10, LocalDateTime.now(), 1L);
		doThrow(new IllegalStateException("Proficiency service failure")).when(adjustProficiencyPortIn).adjust(any(), anyInt());

		try (MockedStatic<JsonParser> mockedJsonParser = mockStatic(JsonParser.class)) {
			mockedJsonParser.when(() -> JsonParser.fromJson(any(), eq(CompleteQuestEvent.class))).thenReturn(event);

			assertThrows(IllegalStateException.class, () -> sut.handleEvent(eventEntry));

			verify(processEventEntryPortIn, times(1)).save(anyString(), anyString());
			verify(adjustProficiencyPortIn, times(1)).adjust(any(), anyInt());
		}
	}

	@DisplayName("성공 이벤트 저장 도중 데이터 베이스 작업이 실패한다.")
	@Test
	void cannot_process_event_by_success_event() {
		EventEntry eventEntry = EventEntry.of("CompleteQuestEvent", "payload", 1L);
		CompleteQuestEvent event = CompleteQuestEvent.of(UUID.randomUUID(), UUID.randomUUID(), "DAILY", 2, 10, LocalDateTime.now(), 1L);
		doThrow(new DataAccessException("process event save error") {
		}).when(processEventEntryPortIn).save(anyString(), anyString());

		try (MockedStatic<JsonParser> mockedJsonParser = mockStatic(JsonParser.class)) {
			mockedJsonParser.when(() -> JsonParser.fromJson(any(), eq(CompleteQuestEvent.class))).thenReturn(event);

			assertThrows(DataAccessException.class, () -> sut.handleEvent(eventEntry));

			verify(processEventEntryPortIn, times(1)).save(anyString(), anyString());
			verifyNoInteractions(adjustProficiencyPortIn);
		}
	}
}
