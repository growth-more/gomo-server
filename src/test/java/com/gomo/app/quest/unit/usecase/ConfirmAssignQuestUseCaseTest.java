package com.gomo.app.quest.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.quest.application.ConfirmAssignQuestUseCase;
import com.gomo.app.quest.common.fixture.AssignQuestFixture;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.exception.AssignQuestAccessDeniedException;

@DisplayName("[Application unit]: 할당 퀘스트 확정 테스트")
@ExtendWith(MockitoExtension.class)
public class ConfirmAssignQuestUseCaseTest {

	@InjectMocks
	private ConfirmAssignQuestUseCase sut;

	@Mock
	private AssignQuestRepository assignQuestRepository;

	@DisplayName("할당 퀘스트를 확정한다.")
	@Test
	void confirm_assign_quest() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest(QuestType.DAILY);
		doReturn(Optional.of(assignQuest)).when(assignQuestRepository).findById(any(AssignQuestId.class));

		sut.confirm(assignQuest.getQuest().getParticipantId().getId(), AssignQuestId.of(UUID.randomUUID()));

		assertThat(assignQuest.isConfirmed()).isTrue();
	}

	@DisplayName("퀘스트 참여자가 아니면 할당 퀘스트를 확정할 수 없다.")
	@Test
	void confirm_assign_quest_with_not_participant() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest(QuestType.DAILY);
		doReturn(Optional.of(assignQuest)).when(assignQuestRepository).findById(any(AssignQuestId.class));

		assertThatThrownBy(
			() -> sut.confirm(UUID.randomUUID(), AssignQuestId.of(UUID.randomUUID())))
			.isInstanceOf(AssignQuestAccessDeniedException.class)
			.hasMessageContaining("Access denied for the assign quest");
	}
}
