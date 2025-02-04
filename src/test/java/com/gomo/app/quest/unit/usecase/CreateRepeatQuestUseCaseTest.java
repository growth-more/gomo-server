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

import com.gomo.app.quest.application.CreateRepeatQuestUseCase;
import com.gomo.app.quest.common.fixture.RepeatQuestFixture;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.service.RepeatQuestService;
import com.gomo.app.quest.presentation.request.CreateRepeatQuestRequest;
import com.gomo.app.quest.presentation.response.CreateRepeatQuestResponse;

@DisplayName("[Application unit]: 반복 퀘스트 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class CreateRepeatQuestUseCaseTest {

	@InjectMocks
	private CreateRepeatQuestUseCase sut;

	@Mock
	private RepeatQuestService repeatQuestService;

	@DisplayName("반복 퀘스트를 생성한다.")
	@Test
	void create_repeat_quest() {
		RepeatQuest repeatQuest = RepeatQuestFixture.repeatQuest();
		doReturn(repeatQuest).when(repeatQuestService).create(any(), any());

		CreateRepeatQuestResponse actual = sut.create(ParticipantId.of(UUID.randomUUID()), createMockRequest());

		assertThat(actual.getId()).isEqualTo(repeatQuest.getId().getId());
	}

	private static @NotNull CreateRepeatQuestRequest createMockRequest() {
		return CreateRepeatQuestRequest.of(UUID.randomUUID(), "subject name", QuestType.DAILY, "quest content");
	}
}
