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

import com.gomo.app.core.quest.application.port.command.CreateRepeatQuestCommand;
import com.gomo.app.core.quest.application.port.dto.ParticipantDto;
import com.gomo.app.core.quest.application.port.out.ParticipantReader;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.repeat.RepeatQuest;
import com.gomo.app.core.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.core.quest.fixture.RepeatQuestFixture;

@DisplayName("[Application unit]: 반복 퀘스트 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class RepeatQuestCreateServiceTest {

	@InjectMocks
	private RepeatQuestCreateService sut;

	@Mock
	private ParticipantReader participantReader;

	@Mock
	private RepeatQuestRepository repeatQuestRepository;

	@Captor
	private ArgumentCaptor<RepeatQuest> repeatQuestCaptor;

	@DisplayName("반복 퀘스트를 생성한다.")
	@Test
	void create_repeat_quest() {
		int maxDisplayOrder = 4;
		RepeatQuest repeatQuest = RepeatQuestFixture.create();
		ParticipantDto participantDto = ParticipantDto.of(UUID.randomUUID(), 5, 5, 5);
		doReturn(participantDto).when(participantReader).read(any());
		doReturn((long)maxDisplayOrder).when(repeatQuestRepository).countByQuestParticipantIdAndQuestType(any(), any());
		doReturn(maxDisplayOrder).when(repeatQuestRepository).findMaxDisplayOrderByQuestType(any(), any());
		doReturn(repeatQuest).when(repeatQuestRepository).save(any());

		UUID actual = sut.create(CreateRepeatQuestCommand.of(UUID.randomUUID(), UUID.randomUUID(), "subject name", QuestType.DAILY.name(), "quest content"));

		assertThat(actual).isEqualTo(repeatQuest.getId());
	}

	@DisplayName("할당량을 초과하면 반복 퀘스트를 생성할 수 없다.")
	@Test
	void check_quest_quota() {
		ParticipantDto participantDto = ParticipantDto.of(UUID.randomUUID(), 5, 5, 5);
		doReturn(participantDto).when(participantReader).read(any());
		doReturn(5L).when(repeatQuestRepository).countByQuestParticipantIdAndQuestType(any(), any());

		assertThatThrownBy(() -> sut.create(CreateRepeatQuestCommand.of(UUID.randomUUID(), UUID.randomUUID(), "subject name", QuestType.DAILY.name(), "quest content")));
	}

	@DisplayName("새로 생성된 반복 퀘스트의 정렬 순서는 현재 등록된 반복 퀘스트의 마지막 번호 + 1이다.")
	@Test
	void create_repeat_quest_with_display_order() {
		int maxDisplayOrder = 4;
		ParticipantDto participantDto = new ParticipantDto(UUID.randomUUID(), 5, 5, 5);
		RepeatQuest savedQuest = RepeatQuest.of(UUID.randomUUID(), null, null);
		doReturn(participantDto).when(participantReader).read(any());
		doReturn((long)maxDisplayOrder).when(repeatQuestRepository).countByQuestParticipantIdAndQuestType(any(), any());
		doReturn(maxDisplayOrder).when(repeatQuestRepository).findMaxDisplayOrderByQuestType(any(), any());
		doReturn(savedQuest).when(repeatQuestRepository).save(any());

		sut.create(CreateRepeatQuestCommand.of(UUID.randomUUID(), UUID.randomUUID(), "subject name", QuestType.DAILY.name(), "quest content"));

		verify(repeatQuestRepository).save(repeatQuestCaptor.capture());
		RepeatQuest actual = repeatQuestCaptor.getValue();
		assertThat(actual.getDisplayOrder().getDisplayOrder()).isEqualTo(maxDisplayOrder + 1);
	}
}
