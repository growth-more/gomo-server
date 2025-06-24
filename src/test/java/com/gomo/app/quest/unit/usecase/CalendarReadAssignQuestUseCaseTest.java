package com.gomo.app.quest.unit.usecase;

import static com.gomo.app.quest.domain.model.QuestType.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

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
import com.gomo.app.quest.fixture.AssignQuestFixture;
import com.gomo.app.quest.presentation.response.CalendarListAssignQuestResponse;

@DisplayName("[Application unit]: 할당 퀘스트 과거 이력 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class CalendarReadAssignQuestUseCaseTest {

	@InjectMocks
	private CalendarReadAssignQuestUseCase sut;

	@Mock
	private AssignQuestRepository assignQuestRepository;

	@DisplayName("월별 할당 퀘스트 목록을 조회한다.")
	@Test
	void find_all() {
		List<AssignQuest> calendars = List.of(AssignQuestFixture.assignQuest(DAILY), AssignQuestFixture.assignQuest(WEEKLY));
		doReturn(calendars).when(assignQuestRepository).findByQuestParticipantIdAndStartDateTimeBetween(any(), any(), any());

		CalendarListAssignQuestResponse actual = sut.findAll(ParticipantId.of(UUID.randomUUID()), 2025, 2, 1);

		assertThat(actual.getAssignQuests().size()).isEqualTo(2);
	}
}
