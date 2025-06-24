package com.gomo.app.quest.unit.usecase;

import static com.gomo.app.quest.domain.model.QuestType.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.quest.application.CalendarReadAssignQuestUseCase;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.exception.InvalidPeriodTypeException;
import com.gomo.app.quest.fixture.AssignQuestFixture;
import com.gomo.app.quest.presentation.response.CalendarListAssignQuestResponse;

@DisplayName("[Application unit]: 할당 퀘스트 과거 이력 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class CalendarReadAssignQuestUseCaseTest {

	@InjectMocks
	private CalendarReadAssignQuestUseCase sut;

	@Mock
	private AssignQuestRepository assignQuestRepository;

	@DisplayName("주어진 날짜를 기준으로 한달 이력을 조회한다.")
	@Test
	void find_all_by_month() {
		List<AssignQuest> calendars = List.of(AssignQuestFixture.assignQuest(DAILY), AssignQuestFixture.assignQuest(WEEKLY));
		ParticipantId participantId = ParticipantId.of(UUID.randomUUID());
		LocalDateTime start = LocalDateTime.of(2025, 2, 1, 0, 0);
		LocalDateTime end = start.plusMonths(1).minusSeconds(1);
		doReturn(calendars).when(assignQuestRepository).findByQuestParticipantIdAndStartDateTimeBetween(
			eq(participantId),
			eq(start),
			eq(end)
		);

		CalendarListAssignQuestResponse actual = sut.findAll(participantId, 2025, 2, 1, "MONTH");

		assertThat(actual.getAssignQuests().size()).isEqualTo(2);
	}

	@DisplayName("주어진 날짜를 기준으로 하루 이력을 조회한다.")
	@Test
	void find_all_by_day() {
		List<AssignQuest> calendars = List.of(AssignQuestFixture.assignQuest(DAILY), AssignQuestFixture.assignQuest(WEEKLY));
		ParticipantId participantId = ParticipantId.of(UUID.randomUUID());
		LocalDateTime start = LocalDateTime.of(2025, 2, 1, 0, 0);
		LocalDateTime end = start.plusDays(1).minusSeconds(1);
		doReturn(calendars).when(assignQuestRepository).findByQuestParticipantIdAndStartDateTimeBetween(
			eq(participantId),
			eq(start),
			eq(end)
		);

		CalendarListAssignQuestResponse actual = sut.findAll(participantId, 2025, 2, 1, "DAY");

		assertThat(actual.getAssignQuests().size()).isEqualTo(2);
	}

	@DisplayName("잘못된 타입으로 이력을 조회한다.")
	@Test
	void find_all_by_invalid_type() {
		assertThatThrownBy(() -> sut.findAll(ParticipantId.of(UUID.randomUUID()), 2025, 2, 1, "INVALID"))
			.isInstanceOf(InvalidPeriodTypeException.class)
			.hasMessageContaining("The requested period type is invalid");
	}
}
