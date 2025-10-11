package com.gomo.app.core.quest.application.usecase;

import static com.gomo.app.core.quest.domain.model.quest.QuestType.*;
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

import com.gomo.app.core.quest.application.port.command.CalendarAssignQuestCommand;
import com.gomo.app.core.quest.application.port.dto.CalendarAssignQuestDto;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.core.quest.fixture.AssignQuestFixture;

@DisplayName("[Application unit]: 할당 퀘스트 과거 이력 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class CalendarAssignQuestUseCaseTest {

	@InjectMocks
	private CalendarAssignQuestUseCase sut;

	@Mock
	private AssignQuestRepository assignQuestRepository;

	@DisplayName("완료한 퀘스트의 한달 이력을 조회한다.")
	@Test
	void find_completed_quest_for_month() {
		List<AssignQuest> calendars = List.of(AssignQuestFixture.create(DAILY), AssignQuestFixture.create(WEEKLY));
		UUID participantId = UUID.randomUUID();
		LocalDateTime start = LocalDateTime.of(2025, 2, 1, 0, 0);
		LocalDateTime end = start.plusMonths(1).minusSeconds(1);
		doReturn(calendars).when(assignQuestRepository).findByQuestParticipantIdAndCompletedDateTimeBetween(
			eq(participantId),
			eq(start),
			eq(end)
		);

		List<CalendarAssignQuestDto> actual = sut.find(CalendarAssignQuestCommand.of(participantId, true, start, end));

		assertThat(actual.size()).isEqualTo(2);
	}

	@DisplayName("완료한 퀘스트의 하루 이력을 조회한다.")
	@Test
	void find_completed_quest_for_day() {
		List<AssignQuest> calendars = List.of(AssignQuestFixture.create(DAILY), AssignQuestFixture.create(WEEKLY));
		UUID participantId = UUID.randomUUID();
		LocalDateTime start = LocalDateTime.of(2025, 2, 1, 0, 0);
		LocalDateTime end = start.plusDays(1).minusSeconds(1);
		doReturn(calendars).when(assignQuestRepository).findByQuestParticipantIdAndCompletedDateTimeBetween(
			eq(participantId),
			eq(start),
			eq(end)
		);

		List<CalendarAssignQuestDto> actual = sut.find(CalendarAssignQuestCommand.of(participantId, true, start, end));

		assertThat(actual.size()).isEqualTo(2);
	}

	@DisplayName("완료하지 못한 퀘스트의 한달 이력을 조회한다.")
	@Test
	void find_not_completed_quest_for_month() {
		List<AssignQuest> calendars = List.of(AssignQuestFixture.create(DAILY), AssignQuestFixture.create(WEEKLY));
		UUID participantId = UUID.randomUUID();
		LocalDateTime start = LocalDateTime.of(2025, 2, 1, 0, 0);
		LocalDateTime end = start.plusMonths(1).minusSeconds(1);
		doReturn(calendars).when(assignQuestRepository).findByQuestParticipantIdAndStartDateTimeBetweenAndIsCompletedFalse(
			eq(participantId),
			eq(start),
			eq(end)
		);

		List<CalendarAssignQuestDto> actual = sut.find(CalendarAssignQuestCommand.of(participantId, false, start, end));

		assertThat(actual.size()).isEqualTo(2);
	}

	@DisplayName("완료하지 못한 퀘스트의 하루 이력을 조회한다.")
	@Test
	void find_not_completed_quest_for_day() {
		List<AssignQuest> calendars = List.of(AssignQuestFixture.create(DAILY), AssignQuestFixture.create(WEEKLY));
		UUID participantId = UUID.randomUUID();
		LocalDateTime start = LocalDateTime.of(2025, 2, 1, 0, 0);
		LocalDateTime end = start.plusDays(1).minusSeconds(1);
		doReturn(calendars).when(assignQuestRepository).findByQuestParticipantIdAndStartDateTimeBetweenAndIsCompletedFalse(
			eq(participantId),
			eq(start),
			eq(end)
		);

		List<CalendarAssignQuestDto> actual = sut.find(CalendarAssignQuestCommand.of(participantId, false, start, end));

		assertThat(actual.size()).isEqualTo(2);
	}
}
