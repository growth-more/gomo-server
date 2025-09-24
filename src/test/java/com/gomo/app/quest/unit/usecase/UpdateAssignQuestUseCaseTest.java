package com.gomo.app.quest.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.quest.application.UpdateAssignQuestUseCase;
import com.gomo.app.core.quest.application.port.command.UpdateAssignQuestCommand;
import com.gomo.app.core.quest.domain.model.AssignQuest;
import com.gomo.app.core.quest.domain.model.AssignQuestId;
import com.gomo.app.core.quest.domain.model.CompletionProof;
import com.gomo.app.core.quest.domain.model.QuestType;
import com.gomo.app.core.quest.domain.service.AssignQuestService;
import com.gomo.app.core.quest.exception.AssignQuestAccessDeniedException;
import com.gomo.app.core.quest.exception.AssignQuestConstraintViolationException;
import com.gomo.app.core.quest.exception.QuestTypeConstraintViolationException;
import com.gomo.app.core.quest.exception.code.AssignQuestErrorCode;
import com.gomo.app.core.quest.exception.code.QuestTypeErrorCode;
import com.gomo.app.quest.fixture.AssignQuestFixture;

@DisplayName("[Application unit]: 할당 퀘스트 수정 테스트")
@ExtendWith(MockitoExtension.class)
public class UpdateAssignQuestUseCaseTest {

	@InjectMocks
	private UpdateAssignQuestUseCase sut;

	@Mock
	private AssignQuestService assignQuestService;

	@DisplayName("할당 퀘스트를 수정한다.")
	@Test
	void update_assign_quest() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest(QuestType.DAILY);
		doReturn(assignQuest).when(assignQuestService).find(any());
		sut.update(getUpdateAssignQuestCommand(assignQuest.getQuest().getParticipantId().getId(), QuestType.DAILY.name()));
		assertThat(assignQuest.getQuest().getSubjectName().toString()).isEqualTo("updated subject name");
	}

	@DisplayName("퀘스트 참여자가 아니면 할당 퀘스트를 수정할 수 없다.")
	@Test
	void update_assign_quest_with_not_participant() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest(QuestType.DAILY);
		doReturn(assignQuest).when(assignQuestService).find(any(AssignQuestId.class));

		assertThatThrownBy(() -> sut.update(getUpdateAssignQuestCommand(UUID.randomUUID(), QuestType.WEEKLY.name())))
			.isInstanceOf(AssignQuestAccessDeniedException.class)
			.hasMessageContaining("Access denied for the assign quest");
	}

	@DisplayName("할당 퀘스트를 다른 퀘스트 타입으로 수정할 수 없다.")
	@Test
	void update_assign_quest_with_different_type() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest(QuestType.DAILY);
		doReturn(assignQuest).when(assignQuestService).find(any(AssignQuestId.class));

		assertThatThrownBy(() -> sut.update(getUpdateAssignQuestCommand(assignQuest.getQuest().getParticipantId().getId(), QuestType.WEEKLY.name())))
			.isInstanceOf(QuestTypeConstraintViolationException.class)
			.hasMessageContaining(QuestTypeErrorCode.MISMATCHED.getMessage());
	}

	@DisplayName("이미 확정한 할당 퀘스트는 수정할 수 없다.")
	@Test
	void update_confirmed_assign_quest() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest(true);
		doReturn(assignQuest).when(assignQuestService).find(any(AssignQuestId.class));

		assertThatThrownBy(() -> sut.update(getUpdateAssignQuestCommand(assignQuest.getQuest().getParticipantId().getId(), QuestType.DAILY.name())))
			.isInstanceOf(AssignQuestConstraintViolationException.class)
			.hasMessageContaining(AssignQuestErrorCode.ALREADY_CONFIRMED.getMessage());
	}

	@DisplayName("이미 완료한 할당 퀘스트는 수정할 수 없다.")
	@Test
	void update_completed_assign_quest() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest(false, true, CompletionProof.createDefault());
		doReturn(assignQuest).when(assignQuestService).find(any(AssignQuestId.class));

		assertThatThrownBy(() -> sut.update(getUpdateAssignQuestCommand(assignQuest.getQuest().getParticipantId().getId(), QuestType.DAILY.name())))
			.isInstanceOf(AssignQuestConstraintViolationException.class)
			.hasMessageContaining(AssignQuestErrorCode.ALREADY_COMPLETED.getMessage());
	}

	private static @NotNull UpdateAssignQuestCommand getUpdateAssignQuestCommand(UUID participantId, String questType) {
		return UpdateAssignQuestCommand.of(participantId, UUID.randomUUID(), UUID.randomUUID(), "updated subject name", questType, "updated quest content");
	}
}
