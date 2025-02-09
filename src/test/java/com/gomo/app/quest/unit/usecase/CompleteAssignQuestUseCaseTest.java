package com.gomo.app.quest.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.common.event.Events;
import com.gomo.app.quest.application.CompleteAssignQuestUseCase;
import com.gomo.app.quest.common.fixture.AssignQuestFixture;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.model.PointReward;
import com.gomo.app.quest.domain.model.QuestReward;
import com.gomo.app.quest.domain.model.ScoreReward;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.domain.service.QuestRewardService;
import com.gomo.app.quest.event.PointQuestCompletedEvent;
import com.gomo.app.quest.event.ScoreQuestCompletedEvent;
import com.gomo.app.quest.event.StreakQuestCompletedEvent;
import com.gomo.app.quest.exception.AssignQuestAccessDeniedException;
import com.gomo.app.quest.presentation.request.CompleteAssignQuestRequest;

@DisplayName("[Application unit]: 할당 퀘스트 완료 테스트")
@ExtendWith(MockitoExtension.class)
public class CompleteAssignQuestUseCaseTest {

	@InjectMocks
	private CompleteAssignQuestUseCase sut;

	@Mock
	private QuestRewardService questRewardService;

	@Mock
	private AssignQuestRepository assignQuestRepository;

	@DisplayName("할당 퀘스트를 완료한다.")
	@Test
	void complete_assign_quest() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest(true);
		doReturn(Optional.of(assignQuest)).when(assignQuestRepository).findById(any(AssignQuestId.class));
		doReturn(QuestReward.of(assignQuest.getId(), ScoreReward.of(2, 1L), PointReward.of(10, 1L))).when(questRewardService).create(any(), any());

		try (MockedStatic<Events> mockedEvents = mockStatic(Events.class)) {
			sut.complete(assignQuest.getQuest().getParticipantId().getId(), AssignQuestId.of(UUID.randomUUID()), createRequest());

			mockedEvents.verify(() -> Events.raise(any(ScoreQuestCompletedEvent.class)), times(1));
			mockedEvents.verify(() -> Events.raise(any(PointQuestCompletedEvent.class)), times(1));
			mockedEvents.verify(() -> Events.raise(any(StreakQuestCompletedEvent.class)), times(1));
		}

		assertThat(assignQuest.isCompleted()).isTrue();
	}

	@DisplayName("퀘스트 참여자가 아니면 할당 퀘스트를 완료할 수 없다.")
	@Test
	void complete_assign_quest_with_not_participant() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest(true);
		doReturn(Optional.of(assignQuest)).when(assignQuestRepository).findById(any());

		assertThatThrownBy(
			() -> sut.complete(UUID.randomUUID(), AssignQuestId.of(UUID.randomUUID()), createRequest()))
			.isInstanceOf(AssignQuestAccessDeniedException.class)
			.hasMessageContaining("Access denied for the assign quest");
	}

	@DisplayName("할당 퀘스트를 완료하면 퀘스트 보상 및 스트릭 이벤트가 발생한다.")
	@Test
	void complete_assign_quest_with_event() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest(true);
		doReturn(Optional.of(assignQuest)).when(assignQuestRepository).findById(any());
		doReturn(QuestReward.of(assignQuest.getId(), ScoreReward.of(2, 1L), PointReward.of(10, 1L))).when(questRewardService).create(any(), any());

		try (MockedStatic<Events> mockedEvents = Mockito.mockStatic(Events.class)) {
			sut.complete(assignQuest.getQuest().getParticipantId().getId(), AssignQuestId.of(UUID.randomUUID()), createRequest());

			mockedEvents.verify(() -> Events.raise(any(ScoreQuestCompletedEvent.class)), times(1));
			mockedEvents.verify(() -> Events.raise(any(PointQuestCompletedEvent.class)), times(1));
			mockedEvents.verify(() -> Events.raise(any(StreakQuestCompletedEvent.class)), times(1));
		}
	}

	private static @NotNull CompleteAssignQuestRequest createRequest() {
		return CompleteAssignQuestRequest.of("https://proof");
	}
}
