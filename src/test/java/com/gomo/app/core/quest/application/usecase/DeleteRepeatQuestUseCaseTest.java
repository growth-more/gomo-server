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

import com.gomo.app.core.quest.domain.model.repeat.RepeatQuest;
import com.gomo.app.core.quest.domain.model.repeat.RepeatQuestId;
import com.gomo.app.core.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.core.quest.domain.service.RepeatQuestService;
import com.gomo.app.core.quest.exception.RepeatQuestAccessDeniedException;
import com.gomo.app.core.quest.exception.code.RepeatQuestErrorCode;
import com.gomo.app.core.quest.fixture.RepeatQuestFixture;

@DisplayName("[Application unit]: 반복 퀘스트 삭제 테스트")
@ExtendWith(MockitoExtension.class)
public class DeleteRepeatQuestUseCaseTest {

	@InjectMocks
	private DeleteRepeatQuestUseCase sut;

	@Mock
	private RepeatQuestService repeatQuestService;

	@Mock
	private RepeatQuestRepository repeatQuestRepository;

	@DisplayName("반복 퀘스트를 삭제한다.")
	@Test
	void delete_repeat_quest() {
		RepeatQuest repeatQuest = RepeatQuestFixture.create();
		doReturn(repeatQuest).when(repeatQuestService).find(any(RepeatQuestId.class));

		sut.delete(repeatQuest.getQuest().getParticipantId().getId(), UUID.randomUUID());

		verify(repeatQuestRepository, times(1)).delete(any());
	}

	@DisplayName("퀘스트 참여자가 아니면 반복 퀘스트를 삭제할 수 없다.")
	@Test
	void delete_repeat_quest_by_not_participant() {
		RepeatQuest repeatQuest = RepeatQuestFixture.create();
		doReturn(repeatQuest).when(repeatQuestService).find(any(RepeatQuestId.class));

		assertThatThrownBy(
			() -> sut.delete(UUID.randomUUID(), UUID.randomUUID()))
			.isInstanceOf(RepeatQuestAccessDeniedException.class)
			.hasMessageContaining(RepeatQuestErrorCode.ACCESS_DENIED.getMessage());
	}
}
