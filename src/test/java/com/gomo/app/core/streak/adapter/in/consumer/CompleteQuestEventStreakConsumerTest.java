package com.gomo.app.core.streak.adapter.in.consumer;

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

import com.gomo.app.common.util.JsonParser;
import com.gomo.app.core.quest.domain.event.CompleteQuestEvent;
import com.gomo.app.core.streak.application.port.in.StreakCreator;
import com.gomo.app.support.evententry.domain.model.EventEntry;

@DisplayName("[Consumer unit]: 퀘스트 완료(스트릭) 이벤트 처리 테스트")
@ExtendWith(MockitoExtension.class)
class CompleteQuestEventStreakConsumerTest {

	@InjectMocks
	private CompleteQuestEventStreakConsumer sut;

	@Mock
	private StreakCreator streakCreator;

	@DisplayName("숙련도 향상 이벤트를 처리한다.")
	@Test
	void event_process() {
		EventEntry eventEntry = EventEntry.of("CompleteQuestEvent", "payload", 1L);
		CompleteQuestEvent event = CompleteQuestEvent.of(UUID.randomUUID(), UUID.randomUUID(), "DAILY", 2, 10, LocalDateTime.now(), 1L);

		try (MockedStatic<JsonParser> mockedStatic = mockStatic(JsonParser.class)) {
			mockedStatic.when(() -> JsonParser.fromJson("payload", CompleteQuestEvent.class)).thenReturn(event);

			sut.handleEvent(eventEntry);

			verify(streakCreator, times(1)).create(any(), anyString(), any());
		}
	}

	@DisplayName("숙련도 향상 작업에 실패한다.")
	@Test
	void cannot_process_event_by_streak() {
		EventEntry eventEntry = EventEntry.of("CompleteQuestEvent", "payload", 1L);
		CompleteQuestEvent event = CompleteQuestEvent.of(UUID.randomUUID(), UUID.randomUUID(), "DAILY", 2, 10, LocalDateTime.now(), 1L);
		doThrow(new IllegalStateException("Streak service failure")).when(streakCreator).create(any(), anyString(), any());

		try (MockedStatic<JsonParser> mockedJsonParser = mockStatic(JsonParser.class)) {
			mockedJsonParser.when(() -> JsonParser.fromJson(any(), eq(CompleteQuestEvent.class))).thenReturn(event);

			assertThrows(IllegalStateException.class, () -> sut.handleEvent(eventEntry));
		}
	}
}
