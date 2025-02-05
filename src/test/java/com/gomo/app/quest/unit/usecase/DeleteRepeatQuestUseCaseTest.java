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

import com.gomo.app.quest.application.DeleteRepeatQuestUseCase;
import com.gomo.app.quest.common.fixture.RepeatQuestFixture;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.model.RepeatQuestId;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.quest.exception.RepeatQuestAccessDeniedException;

@DisplayName("[Application unit]: 반복 퀘스트 삭제 테스트")
@ExtendWith(MockitoExtension.class)
public class DeleteRepeatQuestUseCaseTest {

	@InjectMocks
	private DeleteRepeatQuestUseCase sut;

	@Mock
	private RepeatQuestRepository repeatQuestRepository;

	@DisplayName("반복 퀘스트를 삭제한다.")
	@Test
	void delete_repeat_quest() {
		RepeatQuest repeatQuest = RepeatQuestFixture.repeatQuest();
		doReturn(Optional.of(repeatQuest)).when(repeatQuestRepository).findById(any());

		sut.delete(repeatQuest.getQuest().getParticipantId().getId(), RepeatQuestId.of(UUID.randomUUID()));

		verify(repeatQuestRepository, times(1)).delete(any());
	}

	@DisplayName("퀘스트 참여자가 아니면 반복 퀘스트를 삭제할 수 없다.")
	@Test
	void delete_repeat_quest_by_not_participant() {
		RepeatQuest repeatQuest = RepeatQuestFixture.repeatQuest();
		doReturn(Optional.of(repeatQuest)).when(repeatQuestRepository).findById(any());

		assertThatThrownBy(
			() -> sut.delete(UUID.randomUUID(), RepeatQuestId.of(UUID.randomUUID())))
			.isInstanceOf(RepeatQuestAccessDeniedException.class)
			.hasMessageContaining("Access denied for the repeat quest");
	}
}
