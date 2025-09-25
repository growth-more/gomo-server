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

import com.gomo.app.core.quest.application.port.ReadParticipantPortOut;
import com.gomo.app.core.quest.application.port.command.CreateAssignQuestCommand;
import com.gomo.app.core.quest.application.port.dto.CreateAssignQuestDto;
import com.gomo.app.core.quest.application.port.dto.ParticipantDto;
import com.gomo.app.core.quest.domain.model.AssignQuest;
import com.gomo.app.core.quest.domain.model.QuestType;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.core.quest.domain.service.AssignQuestService;
import com.gomo.app.core.quest.fixture.AssignQuestFixture;

@DisplayName("[Application unit]: 할당 퀘스트 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class CreateAssignQuestUseCaseTest {

	@InjectMocks
	private CreateAssignQuestUseCase sut;

	@Mock
	private ReadParticipantPortOut readParticipantPort;

	@Mock
	private AssignQuestService assignQuestService;

	@Mock
	private AssignQuestRepository assignQuestRepository;

	@DisplayName("할당 퀘스트를 생성한다.")
	@Test
	void create_assign_quest() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest();
		ParticipantDto participantDto = ParticipantDto.of(UUID.randomUUID(), 5, 5, 5);
		doReturn(participantDto).when(readParticipantPort).find(any());
		doReturn(assignQuest).when(assignQuestService).create(any(), any());
		doReturn(4L).when(assignQuestRepository).countParticipatingQuestByQuestType(any(), any(), any(), any());

		CreateAssignQuestDto actual = sut.create(
			CreateAssignQuestCommand.of(UUID.randomUUID(), UUID.randomUUID(), "subject name", QuestType.DAILY.name(), "quest content"));

		assertThat(actual.id()).isEqualTo(assignQuest.getId().getId());
	}

	@DisplayName("할당량을 초과하면 할당 퀘스트를 생성할 수 없다.")
	@Test
	void check_quest_quota() {
		ParticipantDto participantDto = ParticipantDto.of(UUID.randomUUID(), 5, 5, 5);
		doReturn(participantDto).when(readParticipantPort).find(any());
		doReturn(5L).when(assignQuestRepository).countParticipatingQuestByQuestType(any(), any(), any(), any());

		assertThatThrownBy(() -> sut.create(CreateAssignQuestCommand.of(UUID.randomUUID(), UUID.randomUUID(), "subject name", QuestType.DAILY.name(), "quest content")));
	}
}
