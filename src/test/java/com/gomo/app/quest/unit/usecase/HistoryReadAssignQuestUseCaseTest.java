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

import com.gomo.app.common.dto.PageRequest;
import com.gomo.app.quest.application.HistoryReadAssignQuestUseCase;
import com.gomo.app.quest.common.fixture.AssignQuestFixture;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.presentation.response.HistoryListAssignQuestResponse;

@DisplayName("[Application unit]: 할당 퀘스트 과거 이력 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class HistoryReadAssignQuestUseCaseTest {

	@InjectMocks
	private HistoryReadAssignQuestUseCase sut;

	@Mock
	private AssignQuestRepository assignQuestRepository;

	@DisplayName("과거 이력을 조회한다.")
	@Test
	void find_All() {
		List<AssignQuest> dailyQuests = List.of(AssignQuestFixture.assignQuest(DAILY), AssignQuestFixture.assignQuest(DAILY));
		List<AssignQuest> weeklyQuests = List.of(AssignQuestFixture.assignQuest(WEEKLY));
		List<AssignQuest> monthlyQuests = List.of();
		doReturn(dailyQuests).when(assignQuestRepository).findHistoryQuestByQuestType(any(), eq(DAILY.name()), any(), any(), any(), eq(10));
		doReturn(weeklyQuests).when(assignQuestRepository).findHistoryQuestByQuestType(any(), eq(WEEKLY.name()), any(), any(), any(), eq(10));
		doReturn(monthlyQuests).when(assignQuestRepository).findHistoryQuestByQuestType(any(), eq(MONTHLY.name()), any(), any(), any(), eq(10));

		HistoryListAssignQuestResponse actual = sut.findAll(ParticipantId.of(UUID.randomUUID()), PageRequest.of(10, null));

		assertThat(actual.getDailyHistoryQuests().size()).isEqualTo(2);
		assertThat(actual.getWeeklyHistoryQuests().size()).isEqualTo(1);
		assertThat(actual.getMonthlyHistoryQuests().size()).isEqualTo(0);
	}
}
