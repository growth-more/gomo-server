package com.gomo.app.core.quest.application.service;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.quest.application.port.command.CreateQuestPoolCommand;
import com.gomo.app.core.quest.application.port.dto.QuestContentDto;
import com.gomo.app.core.quest.application.port.out.QuestContentCreator;
import com.gomo.app.core.quest.domain.repository.QuestPoolRepository;

@DisplayName("[Application unit]: 퀘스트 풀 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class QuestPoolCreateServiceTest {

	@InjectMocks
	private QuestPoolCreateService sut;

	@Mock
	private QuestContentCreator questContentCreator;

	@Mock
	private QuestPoolRepository questPoolRepository;

	@DisplayName("지정된 타입, 개수만큼 사용자의 퀘스트 풀을 생성한다.")
	@Test
	void create_quest_pool() {
		List<QuestContentDto> questContents = List.of(
			QuestContentDto.of(UUID.randomUUID(), UUID.randomUUID(), "name1", "DAILY", "content1"),
			QuestContentDto.of(UUID.randomUUID(), UUID.randomUUID(), "name2", "DAILY", "content2"),
			QuestContentDto.of(UUID.randomUUID(), UUID.randomUUID(), "name3", "DAILY", "content3")
		);
		doReturn(10L).when(questPoolRepository).countByQuestParticipantIdAndQuestTypeAndProcessingStatus(any(), any(), any());
		doReturn(questContents).when(questContentCreator).create(any());
		CreateQuestPoolCommand createQuestPoolCommand = CreateQuestPoolCommand.of(
			UUID.randomUUID(),
			List.of(
				CreateQuestPoolCommand.Subject.of(UUID.randomUUID(), "name", 10),
				CreateQuestPoolCommand.Subject.of(UUID.randomUUID(), "name", 15)
			),
			"DAILY",
			20
		);

		sut.create(createQuestPoolCommand);

		verify(questPoolRepository, times(1)).saveAll(any());
	}

	@DisplayName("생성하고자 하는 수만큼 이미 퀘스트 풀이 존재한다.")
	@Test
	void already_full_quest_pools() {
		doReturn(20L).when(questPoolRepository).countByQuestParticipantIdAndQuestTypeAndProcessingStatus(any(), any(), any());
		CreateQuestPoolCommand createQuestPoolCommand = CreateQuestPoolCommand.of(
			UUID.randomUUID(),
			List.of(
				CreateQuestPoolCommand.Subject.of(UUID.randomUUID(), "name", 10),
				CreateQuestPoolCommand.Subject.of(UUID.randomUUID(), "name", 15)
			),
			"DAILY",
			20
		);
		sut.create(createQuestPoolCommand);

		verify(questPoolRepository, times(0)).saveAll(any());
	}

	@DisplayName("생성하고자 하는 수보다 많이큼 이미 퀘스트 풀이 존재한다.")
	@Test
	void already_over_quest_pools() {
		doReturn(30L).when(questPoolRepository).countByQuestParticipantIdAndQuestTypeAndProcessingStatus(any(), any(), any());
		CreateQuestPoolCommand createQuestPoolCommand = CreateQuestPoolCommand.of(
			UUID.randomUUID(),
			List.of(
				CreateQuestPoolCommand.Subject.of(UUID.randomUUID(), "name", 10),
				CreateQuestPoolCommand.Subject.of(UUID.randomUUID(), "name", 15)
			),
			"DAILY",
			20
		);
		sut.create(createQuestPoolCommand);

		verify(questPoolRepository, times(0)).saveAll(any());
	}

	@DisplayName("요청한 사용자를 위한 퀘스트 내용이 하나도 생성되지 않으면, 퀘스트 풀 또한 저장되지 않는다.")
	@Test
	void no_create_quest_pool_for_participant() {
		doReturn(10L).when(questPoolRepository).countByQuestParticipantIdAndQuestTypeAndProcessingStatus(any(), any(), any());
		doReturn(List.of()).when(questContentCreator).create(any());
		CreateQuestPoolCommand createQuestPoolCommand = CreateQuestPoolCommand.of(
			UUID.randomUUID(),
			List.of(
				CreateQuestPoolCommand.Subject.of(UUID.randomUUID(), "name", 10),
				CreateQuestPoolCommand.Subject.of(UUID.randomUUID(), "name", 15)
			),
			"DAILY",
			20
		);

		sut.create(createQuestPoolCommand);

		verify(questPoolRepository, times(0)).saveAll(any());
	}
}
