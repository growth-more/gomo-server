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
import com.gomo.app.core.quest.application.port.command.CreateRepeatQuestCommand;
import com.gomo.app.core.quest.application.port.dto.ParticipantDto;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.repeat.RepeatQuest;
import com.gomo.app.core.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.core.quest.domain.service.RepeatQuestService;
import com.gomo.app.core.quest.fixture.RepeatQuestFixture;

@DisplayName("[Application unit]: 반복 퀘스트 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class CreateRepeatQuestUseCaseTest {

	@InjectMocks
	private CreateRepeatQuestUseCase sut;

	@Mock
	private ReadParticipantPortOut readParticipantPort;

	@Mock
	private RepeatQuestService repeatQuestService;

	@Mock
	private RepeatQuestRepository repeatQuestRepository;

	@DisplayName("반복 퀘스트를 생성한다.")
	@Test
	void create_repeat_quest() {
		RepeatQuest repeatQuest = RepeatQuestFixture.create();
		ParticipantDto participantDto = ParticipantDto.of(UUID.randomUUID(), 5, 5, 5);
		doReturn(participantDto).when(readParticipantPort).find(any());
		doReturn(repeatQuest).when(repeatQuestService).create(any(), any());
		doReturn(4L).when(repeatQuestRepository).countByQuestParticipantIdAndQuestType(any(), any());

		UUID actual = sut.create(CreateRepeatQuestCommand.of(UUID.randomUUID(), UUID.randomUUID(), "subject name", QuestType.DAILY.name(), "quest content"));

		assertThat(actual).isEqualTo(repeatQuest.id());
	}

	@DisplayName("할당량을 초과하면 반복 퀘스트를 생성할 수 없다.")
	@Test
	void check_quest_quota() {
		ParticipantDto participantDto = ParticipantDto.of(UUID.randomUUID(), 5, 5, 5);
		doReturn(participantDto).when(readParticipantPort).find(any());
		doReturn(5L).when(repeatQuestRepository).countByQuestParticipantIdAndQuestType(any(), any());

		assertThatThrownBy(() -> sut.create(CreateRepeatQuestCommand.of(UUID.randomUUID(), UUID.randomUUID(), "subject name", QuestType.DAILY.name(), "quest content")));
	}
}
