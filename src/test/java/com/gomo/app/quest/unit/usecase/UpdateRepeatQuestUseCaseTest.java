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

import com.gomo.app.quest.application.UpdateRepeatQuestUseCase;
import com.gomo.app.quest.application.port.command.UpdateRepeatQuestCommand;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.model.RepeatQuestId;
import com.gomo.app.quest.domain.service.RepeatQuestService;
import com.gomo.app.quest.exception.QuestTypeConstraintViolationException;
import com.gomo.app.quest.exception.RepeatQuestAccessDeniedException;
import com.gomo.app.quest.exception.code.QuestTypeErrorCode;
import com.gomo.app.quest.exception.code.RepeatQuestErrorCode;
import com.gomo.app.quest.fixture.RepeatQuestFixture;

@DisplayName("[Application unit]: 반복 퀘스트 수정 테스트")
@ExtendWith(MockitoExtension.class)
public class UpdateRepeatQuestUseCaseTest {

	@InjectMocks
	private UpdateRepeatQuestUseCase sut;

	@Mock
	private RepeatQuestService repeatQuestService;

	@DisplayName("반복 퀘스트를 수정한다.")
	@Test
	void update_repeat_quest() {
		RepeatQuest repeatQuest = RepeatQuestFixture.repeatQuest(QuestType.DAILY);
		doReturn(repeatQuest).when(repeatQuestService).find(any(RepeatQuestId.class));
		sut.update(getUpdateRepeatQuestCommand(repeatQuest.getQuest().getParticipantId().getId(), QuestType.DAILY.name()));
		assertThat(repeatQuest.getQuest().getSubjectName().toString()).isEqualTo("updated subject name");
	}

	@DisplayName("퀘스트 참여자가 아니면 할당 퀘스트를 수정할 수 없다.")
	@Test
	void update_repeat_quest_with_not_participant() {
		RepeatQuest repeatQuest = RepeatQuestFixture.repeatQuest(QuestType.DAILY);
		doReturn(repeatQuest).when(repeatQuestService).find(any(RepeatQuestId.class));
		assertThatThrownBy(() -> sut.update(getUpdateRepeatQuestCommand(UUID.randomUUID(), QuestType.WEEKLY.name())))
			.isInstanceOf(RepeatQuestAccessDeniedException.class)
			.hasMessageContaining(RepeatQuestErrorCode.ACCESS_DENIED.getMessage());
	}

	@DisplayName("반복 퀘스트를 다른 퀘스트 타입으로 수정할 수 없다.")
	@Test
	void update_repeat_quest_with_different_type() {
		RepeatQuest repeatQuest = RepeatQuestFixture.repeatQuest(QuestType.DAILY);
		doReturn(repeatQuest).when(repeatQuestService).find(any(RepeatQuestId.class));
		assertThatThrownBy(() -> sut.update(getUpdateRepeatQuestCommand(repeatQuest.getQuest().getParticipantId().getId(), QuestType.WEEKLY.name())))
			.isInstanceOf(QuestTypeConstraintViolationException.class)
			.hasMessageContaining(QuestTypeErrorCode.MISMATCHED.getMessage());
	}

	private static @NotNull UpdateRepeatQuestCommand getUpdateRepeatQuestCommand(UUID participantId, String questType) {
		return UpdateRepeatQuestCommand.of(participantId, UUID.randomUUID(), UUID.randomUUID(), "updated subject name", questType, "updated quest content");
	}
}
