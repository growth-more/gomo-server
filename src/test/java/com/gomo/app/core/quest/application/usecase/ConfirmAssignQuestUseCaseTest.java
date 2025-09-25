package com.gomo.app.core.quest.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.quest.domain.model.AssignQuest;
import com.gomo.app.core.quest.domain.model.AssignQuestId;
import com.gomo.app.core.quest.domain.model.QuestType;
import com.gomo.app.core.quest.domain.service.AssignQuestService;
import com.gomo.app.core.quest.exception.AssignQuestAccessDeniedException;
import com.gomo.app.core.quest.exception.code.AssignQuestErrorCode;
import com.gomo.app.core.quest.fixture.AssignQuestFixture;

@DisplayName("[Application unit]: 할당 퀘스트 확정 테스트")
@ExtendWith(MockitoExtension.class)
public class ConfirmAssignQuestUseCaseTest {

	@InjectMocks
	private ConfirmAssignQuestUseCase sut;

	@Mock
	private AssignQuestService assignQuestService;

	@DisplayName("할당 퀘스트를 확정한다.")
	@Test
	void confirm_assign_quest() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest(QuestType.DAILY);
		doReturn(assignQuest).when(assignQuestService).find(any(AssignQuestId.class));

		sut.confirm(assignQuest.getQuest().getParticipantId().getId(), UUID.randomUUID());

		assertThat(assignQuest.isConfirmed()).isTrue();
	}

	@DisplayName("퀘스트 참여자가 아니면 할당 퀘스트를 확정할 수 없다.")
	@Test
	void confirm_assign_quest_with_not_participant() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest(QuestType.DAILY);
		doReturn(assignQuest).when(assignQuestService).find(any(AssignQuestId.class));

		assertThatThrownBy(
			() -> sut.confirm(UUID.randomUUID(), UUID.randomUUID()))
			.isInstanceOf(AssignQuestAccessDeniedException.class)
			.hasMessageContaining(AssignQuestErrorCode.ACCESS_DENIED.getMessage());
	}
}
