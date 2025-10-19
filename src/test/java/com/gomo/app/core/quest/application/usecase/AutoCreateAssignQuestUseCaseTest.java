package com.gomo.app.core.quest.application.usecase;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.quest.application.port.dto.ParticipantDto;
import com.gomo.app.core.quest.domain.model.pool.QuestPool;
import com.gomo.app.core.quest.domain.model.repeat.RepeatQuest;
import com.gomo.app.core.quest.domain.repository.BulkAssignQuestRepository;
import com.gomo.app.core.quest.domain.repository.QuestPoolRepository;
import com.gomo.app.core.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.core.quest.fixture.QuestPoolFixture;
import com.gomo.app.core.quest.fixture.RepeatQuestFixture;

@DisplayName("[Domain integration]: 참여 퀘스트 생성 스케줄링 기능 테스트")
@ExtendWith(MockitoExtension.class)
public class AutoCreateAssignQuestUseCaseTest {

	@InjectMocks
	private AutoCreateAssignQuestUseCase sut;

	@Mock
	private RepeatQuestRepository repeatQuestRepository;

	@Mock
	private QuestPoolRepository questPoolRepository;

	@Mock
	private BulkAssignQuestRepository bulkAssignQuestRepository;

	// TODO [2025-10-19] jhl221123 : Fake 기반으로 더 정교하게 테스트하도록 개선이 필요합니다.
	@DisplayName("사용자가 등록한 반복 퀘스트와 퀘스트 풀을 기반으로 퀘스트 루틴을 실행한다.")
	@Test
	void auto_create_assign_quests() {
		List<ParticipantDto> participantDtos = List.of(
			ParticipantDto.of(UUID.randomUUID(), 5, 5, 5),
			ParticipantDto.of(UUID.randomUUID(), 10, 10, 10)
		);
		List<RepeatQuest> repeatQuests = List.of(RepeatQuestFixture.create(), RepeatQuestFixture.create());
		List<QuestPool> questPools = List.of(QuestPoolFixture.create(), QuestPoolFixture.create());

		doReturn(repeatQuests).when(repeatQuestRepository).findRepeatQuestsByQuestType(any(), any());
		doReturn(100L).when(questPoolRepository).countByQuestParticipantIdAndQuestTypeAndProcessingStatus(any(), any(), any());
		doReturn(questPools).when(questPoolRepository).findByQuestParticipantIdAndQuestTypeAndProcessingStatus(any(), any(), any(), any());

		sut.execute(participantDtos, "DAILY");

		verify(bulkAssignQuestRepository, times(1)).saveAll(any());
	}
}
