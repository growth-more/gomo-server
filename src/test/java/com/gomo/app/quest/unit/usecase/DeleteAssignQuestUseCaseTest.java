package com.gomo.app.quest.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.quest.application.DeleteAssignQuestUseCase;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.model.CompletionProof;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.domain.service.AssignQuestService;
import com.gomo.app.quest.exception.AssignQuestAccessDeniedException;
import com.gomo.app.quest.exception.AssignQuestConstraintViolationException;
import com.gomo.app.quest.exception.code.AssignQuestErrorCode;
import com.gomo.app.quest.fixture.AssignQuestFixture;

@DisplayName("[Application unit]: 할당 퀘스트 삭제 테스트")
@ExtendWith(MockitoExtension.class)
public class DeleteAssignQuestUseCaseTest {

	@InjectMocks
	private DeleteAssignQuestUseCase sut;

	@Mock
	private AssignQuestService assignQuestService;

	@Mock
	private AssignQuestRepository assignQuestRepository;

	@DisplayName("할당 퀘스트를 삭제한다.")
	@Test
	void delete_assign_quest() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest();
		doReturn(assignQuest).when(assignQuestService).find(any(AssignQuestId.class));

		sut.delete(assignQuest.getQuest().getParticipantId().getId(), AssignQuestId.of(UUID.randomUUID()));

		verify(assignQuestRepository, times(1)).delete(any(AssignQuest.class));
	}

	@DisplayName("퀘스트 참여자가 아니면 할당 퀘스트를 삭제할 수 없다.")
	@Test
	void delete_assign_quest_by_not_participant() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest();
		doReturn(assignQuest).when(assignQuestService).find(any(AssignQuestId.class));

		assertThatThrownBy(
			() -> sut.delete(UUID.randomUUID(), AssignQuestId.of(UUID.randomUUID())))
			.isInstanceOf(AssignQuestAccessDeniedException.class)
			.hasMessageContaining(AssignQuestErrorCode.ACCESS_DENIED.getMessage());
	}

	@DisplayName("이미 확정한 할당 퀘스트는 삭제할 수 없다.")
	@Test
	void delete_confirmed_assign_quest() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest(true);
		doReturn(assignQuest).when(assignQuestService).find(any(AssignQuestId.class));

		assertThatThrownBy(
			() -> sut.delete(assignQuest.getQuest().getParticipantId().getId(), AssignQuestId.of(UUID.randomUUID())))
			.isInstanceOf(AssignQuestConstraintViolationException.class)
			.hasMessageContaining(AssignQuestErrorCode.ALREADY_CONFIRMED.getMessage());
	}

	@DisplayName("이미 완료한 할당 퀘스트는 삭제할 수 없다.")
	@Test
	void delete_completed_assign_quest() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest(false, true, CompletionProof.createDefault());
		doReturn(assignQuest).when(assignQuestService).find(any(AssignQuestId.class));

		assertThatThrownBy(
			() -> sut.delete(assignQuest.getQuest().getParticipantId().getId(), AssignQuestId.of(UUID.randomUUID())))
			.isInstanceOf(AssignQuestConstraintViolationException.class)
			.hasMessageContaining(AssignQuestErrorCode.ALREADY_COMPLETED.getMessage());
	}
}
