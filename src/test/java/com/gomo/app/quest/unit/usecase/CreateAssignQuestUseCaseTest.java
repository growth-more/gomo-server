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

import com.gomo.app.quest.application.CreateAssignQuestUseCase;
import com.gomo.app.quest.common.fixture.AssignQuestFixture;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.service.AssignQuestService;
import com.gomo.app.quest.presentation.request.CreateAssignQuestRequest;
import com.gomo.app.quest.presentation.response.CreateAssignQuestResponse;

@DisplayName("[Application unit]: 할당 퀘스트 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class CreateAssignQuestUseCaseTest {

	@InjectMocks
	private CreateAssignQuestUseCase sut;

	@Mock
	private AssignQuestService assignQuestService;

	@DisplayName("할당 퀘스트를 생성한다.")
	@Test
	void create_assign_quest() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest();
		doReturn(assignQuest).when(assignQuestService).create(any(), any());

		CreateAssignQuestResponse actual = sut.create(ParticipantId.of(UUID.randomUUID()), createRequest());

		assertThat(actual.getId()).isEqualTo(assignQuest.getId().getId());
	}

	private static @NotNull CreateAssignQuestRequest createRequest() {
		return CreateAssignQuestRequest.of(UUID.randomUUID(), "subject name", QuestType.DAILY, "quest content");
	}
}
