package com.gomo.app.core.quest.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.quest.application.port.command.CreateAssignQuestCommand;
import com.gomo.app.core.quest.application.port.dto.ParticipantDto;
import com.gomo.app.core.quest.application.port.out.ParticipantReader;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.core.quest.fixture.AssignQuestFixture;

@DisplayName("[Application unit]: 할당 퀘스트 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class AssignQuestCreateServiceTest {

	@InjectMocks
	private AssignQuestCreateService sut;

	@Mock
	private ParticipantReader participantReader;

	@Mock
	private AssignQuestRepository assignQuestRepository;

	@Captor
	private ArgumentCaptor<AssignQuest> assignQuestCaptor;

	@DisplayName("할당 퀘스트를 생성한다.")
	@Test
	void create_assign_quest() {
		AssignQuest assignQuest = AssignQuestFixture.create();
		ParticipantDto participantDto = ParticipantDto.of(UUID.randomUUID(), 5, 5, 5);
		doReturn(participantDto).when(participantReader).read(any());
		doReturn(4L).when(assignQuestRepository).countParticipatingQuestByQuestType(any(), any(), any(), any());
		doReturn(4).when(assignQuestRepository).findMaxDisplayOrderOfParticipatingQuest(any(), any(), any(), any());
		doReturn(assignQuest).when(assignQuestRepository).save(any());

		UUID actual = sut.create(CreateAssignQuestCommand.of(UUID.randomUUID(), UUID.randomUUID(), "subject name", QuestType.DAILY.name(), "quest content"));

		assertThat(actual).isEqualTo(assignQuest.getId());
	}

	@DisplayName("할당량을 초과하면 할당 퀘스트를 생성할 수 없다.")
	@Test
	void check_quest_quota() {
		ParticipantDto participantDto = ParticipantDto.of(UUID.randomUUID(), 5, 5, 5);
		doReturn(participantDto).when(participantReader).read(any());
		doReturn(5L).when(assignQuestRepository).countParticipatingQuestByQuestType(any(), any(), any(), any());

		assertThatThrownBy(() -> sut.create(CreateAssignQuestCommand.of(UUID.randomUUID(), UUID.randomUUID(), "subject name", QuestType.DAILY.name(), "quest content")));
	}

	@DisplayName("새로 생성된 할당 퀘스트의 정렬 순서는 현재 참여중인 퀘스트의 마지막 번호 + 1이다.")
	@Test
	void create_assign_quest_with_display_order() {
		int maxDisplayOrder = 4;
		ParticipantDto participantDto = new ParticipantDto(UUID.randomUUID(), 5, 5, 5);
		doReturn(participantDto).when(participantReader).read(any());
		doReturn((long)maxDisplayOrder).when(assignQuestRepository).countParticipatingQuestByQuestType(any(), any(), any(), any());
		doReturn(maxDisplayOrder).when(assignQuestRepository).findMaxDisplayOrderOfParticipatingQuest(any(), any(), any(), any());
		doReturn(AssignQuestFixture.create()).when(assignQuestRepository).save(any());

		sut.create(new CreateAssignQuestCommand(UUID.randomUUID(), UUID.randomUUID(), "subject name", QuestType.DAILY.name(), "quest content"));

		verify(assignQuestRepository).save(assignQuestCaptor.capture());
		AssignQuest actual = assignQuestCaptor.getValue();
		assertThat(actual.getDisplayOrder().getDisplayOrder()).isEqualTo(maxDisplayOrder + 1);
	}
}
