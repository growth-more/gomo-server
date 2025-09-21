package com.gomo.app.quest.unit.usecase;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.quest.application.FillQuestPoolUseCase;
import com.gomo.app.quest.application.port.CreateQuestContentPort;
import com.gomo.app.quest.application.port.ReadActiveParticipantPort;
import com.gomo.app.quest.application.port.ReadSubjectPort;
import com.gomo.app.quest.application.port.dto.ActiveParticipantDto;
import com.gomo.app.quest.application.port.dto.QuestContentDto;
import com.gomo.app.quest.application.port.dto.SubjectDto;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.repository.QuestPoolRepository;

@DisplayName("[Application unit]: 퀘스트 풀 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class FillQuestPoolUseCaseTest {

	@InjectMocks
	private FillQuestPoolUseCase sut;

	@Mock
	private ReadActiveParticipantPort readActiveParticipantPort;

	@Mock
	private ReadSubjectPort readSubjectPort;

	@Mock
	private CreateQuestContentPort createQuestContentPort;

	@Mock
	private QuestPoolRepository questPoolRepository;

	@DisplayName("지정된 타입, 개수만큼 전체 활성화 사용자의 퀘스트 풀을 생성한다.")
	@Test
	void fill_quest_pool_for_active_participants() {
		List<ActiveParticipantDto> activeParticipants = List.of(
			ActiveParticipantDto.of(UUID.randomUUID()),
			ActiveParticipantDto.of(UUID.randomUUID())
		);

		List<SubjectDto> subjects = List.of(
			SubjectDto.of(UUID.randomUUID(), UUID.randomUUID(), "name1", 10),
			SubjectDto.of(UUID.randomUUID(), UUID.randomUUID(), "name2", 20),
			SubjectDto.of(UUID.randomUUID(), UUID.randomUUID(), "name3", 30)
		);

		List<QuestContentDto> questContents = List.of(
			QuestContentDto.of(UUID.randomUUID(), UUID.randomUUID(), "name1", "DAILY", "content1"),
			QuestContentDto.of(UUID.randomUUID(), UUID.randomUUID(), "name2", "DAILY", "content2"),
			QuestContentDto.of(UUID.randomUUID(), UUID.randomUUID(), "name3", "DAILY", "content3")
		);

		doReturn(activeParticipants).when(readActiveParticipantPort).findAll();
		doReturn(subjects).when(readSubjectPort).findAll(any());
		doReturn(questContents).when(createQuestContentPort).create(any());

		sut.fillForAllActiveParticipants(QuestType.DAILY, 3);

		verify(questPoolRepository, times(activeParticipants.size())).saveAll(any());
	}
}
