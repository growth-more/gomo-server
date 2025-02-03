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
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.common.exception.PolicyViolationException;
import com.gomo.app.quest.application.UpdateAssignQuestUseCase;
import com.gomo.app.quest.common.fixture.AssignQuestFixture;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.model.CompletionProof;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.exception.AssignQuestAccessDeniedException;
import com.gomo.app.quest.presentation.request.UpdateAssignQuestRequest;

@DisplayName("[Application unit]: 할당 퀘스트 수정 테스트")
@ExtendWith(MockitoExtension.class)
public class UpdateAssignQuestUseCaseTest {

	@InjectMocks
	private UpdateAssignQuestUseCase sut;

	@Mock
	private AssignQuestRepository assignQuestRepository;

	@DisplayName("할당 퀘스트를 수정한다.")
	@Test
	void update_assign_quest() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest(QuestType.DAILY);
		doReturn(Optional.of(assignQuest)).when(assignQuestRepository).findById(any());

		sut.update(assignQuest.getQuest().getParticipantId().getId(), AssignQuestId.of(UUID.randomUUID()), createMockRequest(QuestType.DAILY));

		assertThat(assignQuest.getQuest().getSubjectName().toString()).isEqualTo("updated subject name");
	}

	@DisplayName("퀘스트 참여자가 아니면 할당 퀘스트를 수정할 수 없다.")
	@Test
	void update_assign_quest_with_not_participant() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest(QuestType.DAILY);
		doReturn(Optional.of(assignQuest)).when(assignQuestRepository).findById(any());

		assertThatThrownBy(
			() -> sut.update(UUID.randomUUID(), AssignQuestId.of(UUID.randomUUID()), createMockRequest(QuestType.WEEKLY)))
			.isInstanceOf(AssignQuestAccessDeniedException.class)
			.hasMessageContaining("Access denied for the assign quest");
	}

	@DisplayName("할당 퀘스트를 다른 퀘스트 타입으로 수정할 수 없다.")
	@Test
	void update_assign_quest_with_different_type() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest(QuestType.DAILY);
		doReturn(Optional.of(assignQuest)).when(assignQuestRepository).findById(any());

		assertThatThrownBy(
			() -> sut.update(assignQuest.getQuest().getParticipantId().getId(), AssignQuestId.of(UUID.randomUUID()), createMockRequest(QuestType.WEEKLY)))
			.isInstanceOf(PolicyViolationException.class)
			.hasMessageContaining("Assign quest can only be modified within the same type");
	}

	@DisplayName("이미 확정한 할당 퀘스트는 수정할 수 없다.")
	@Test
	void update_confirmed_assign_quest() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest(true);
		doReturn(Optional.of(assignQuest)).when(assignQuestRepository).findById(any());

		assertThatThrownBy(
			() -> sut.update(assignQuest.getQuest().getParticipantId().getId(), AssignQuestId.of(UUID.randomUUID()), createMockRequest(QuestType.DAILY)))
			.isInstanceOf(PolicyViolationException.class)
			.hasMessageContaining("Assign quests cannot be modified once confirmed");
	}

	@DisplayName("이미 완료한 할당 퀘스트는 수정할 수 없다.")
	@Test
	void update_completed_assign_quest() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest(true, CompletionProof.createDefault());
		doReturn(Optional.of(assignQuest)).when(assignQuestRepository).findById(any());

		assertThatThrownBy(
			() -> sut.update(assignQuest.getQuest().getParticipantId().getId(), AssignQuestId.of(UUID.randomUUID()), createMockRequest(QuestType.DAILY)))
			.isInstanceOf(PolicyViolationException.class)
			.hasMessageContaining("Assign quests cannot be modified once completed");
	}

	private static @NotNull UpdateAssignQuestRequest createMockRequest(QuestType questType) {
		return UpdateAssignQuestRequest.of(UUID.randomUUID(), "updated subject name", questType, "updated quest content");
	}
}
