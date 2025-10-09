package com.gomo.app.core.quest.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.quest.application.port.command.CompleteAssignQuestCommand;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.model.assign.AssignQuestId;
import com.gomo.app.core.quest.domain.model.reward.PointReward;
import com.gomo.app.core.quest.domain.model.reward.QuestReward;
import com.gomo.app.core.quest.domain.model.reward.ScoreReward;
import com.gomo.app.core.quest.domain.service.AssignQuestService;
import com.gomo.app.core.quest.domain.service.QuestRewardService;
import com.gomo.app.core.quest.exception.AssignQuestAccessDeniedException;
import com.gomo.app.core.quest.fixture.AssignQuestFixture;
import com.gomo.app.support.evententry.application.port.CreateEventEntryPortIn;

@DisplayName("[Application unit]: 할당 퀘스트 완료 테스트")
@ExtendWith(MockitoExtension.class)
public class CompleteAssignQuestUseCaseTest {

	@InjectMocks
	private CompleteAssignQuestUseCase sut;

	@Mock
	private AssignQuestService assignQuestService;

	@Mock
	private QuestRewardService questRewardService;

	@Mock
	private CreateEventEntryPortIn createEventEntryPortIn;

	@DisplayName("할당 퀘스트를 완료한다.")
	@Test
	void complete_assign_quest() {
		AssignQuest assignQuest = AssignQuestFixture.create(true);
		doReturn(assignQuest).when(assignQuestService).find(any(AssignQuestId.class));
		doReturn(QuestReward.of(assignQuest.getId(), ScoreReward.of(2), PointReward.of(10))).when(questRewardService).create(any(), any());

		LocalDateTime now = LocalDateTime.now();
		sut.complete(CompleteAssignQuestCommand.of(assignQuest.participantId(), UUID.randomUUID(), "https://proof", now));

		assertThat(assignQuest.isCompleted()).isTrue();
		verify(createEventEntryPortIn, times(1)).create(any(), any(), anyLong());
	}

	@DisplayName("퀘스트 참여자가 아니면 할당 퀘스트를 완료할 수 없다.")
	@Test
	void complete_assign_quest_with_not_participant() {
		AssignQuest assignQuest = AssignQuestFixture.create(true);
		doReturn(assignQuest).when(assignQuestService).find(any(AssignQuestId.class));

		assertThatThrownBy(
			() -> sut.complete(CompleteAssignQuestCommand.of(UUID.randomUUID(), UUID.randomUUID(), "https://proof", LocalDateTime.now())))
			.isInstanceOf(AssignQuestAccessDeniedException.class)
			.hasMessageContaining("Access denied for the assign quest");
	}

	@DisplayName("할당 퀘스트를 완료하면 퀘스트 보상 및 스트릭 이벤트가 발생한다.")
	@Test
	void complete_assign_quest_with_event() {
		AssignQuest assignQuest = AssignQuestFixture.create(true);
		doReturn(assignQuest).when(assignQuestService).find(any(AssignQuestId.class));
		doReturn(QuestReward.of(assignQuest.getId(), ScoreReward.of(2), PointReward.of(10))).when(questRewardService).create(any(), any());

		sut.complete(CompleteAssignQuestCommand.of(assignQuest.participantId(), UUID.randomUUID(), "https://proof", LocalDateTime.now()));

		verify(createEventEntryPortIn, times(1)).create(any(), any(), anyLong());
	}
}
