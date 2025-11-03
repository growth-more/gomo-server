package com.gomo.app.core.quest.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.quest.application.port.dto.AssignQuestDetailDto;
import com.gomo.app.core.quest.domain.exception.QuestPoolNotFoundException;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.model.pool.QuestPool;
import com.gomo.app.core.quest.domain.model.reward.QuestReward;
import com.gomo.app.core.quest.domain.repository.QuestPoolRepository;
import com.gomo.app.core.quest.domain.service.QuestRewardProvider;
import com.gomo.app.core.quest.fixture.AssignQuestFixture;
import com.gomo.app.core.quest.fixture.QuestPoolFixture;
import com.gomo.app.core.quest.fixture.QuestRewardFixture;

@DisplayName("[Application unit]: 퀘스트 재생성(리롤) 테스트")
@ExtendWith(MockitoExtension.class)
class AssignQuestReRollServiceTest {

	@InjectMocks
	private AssignQuestReRollService sut;

	@Mock
	private QuestPoolRepository questPoolRepository;

	@Mock
	private AssignQuestService assignQuestService;

	@Mock
	private QuestRewardProvider questRewardProvider;

	@DisplayName("퀘스트를 재생성한다.")
	@Test
	void re_roll_assign_quest() {
		AssignQuest assignQuest = AssignQuestFixture.create();
		QuestPool questPool = QuestPoolFixture.create();
		QuestReward questReward = QuestRewardFixture.create();
		doReturn(assignQuest).when(assignQuestService).readById(any());
		doReturn(Optional.of(questPool)).when(questPoolRepository).findFirstByQuestParticipantIdAndQuestTypeAndSourceTypeAndProcessingStatus(any(), any(), any(), any());
		doReturn(questReward).when(questRewardProvider).provide(any());

		AssignQuestDetailDto actual = sut.reRoll(assignQuest.participantId(), assignQuest.getId());

		assertThat(actual.questType()).isEqualTo(assignQuest.questType().name());
		assertThat(actual.content()).isEqualTo(questPool.getQuest().getContent().toString());
		assertThat(actual.displayOrder()).isEqualTo(assignQuest.displayOrder());
	}

	@DisplayName("사용 가능한 QuestPool이 없다면, 재생성에 실패한다.")
	@Test
	void re_roll_assign_quest_without_quest_pool() {
		AssignQuest removedAssignQuest = AssignQuestFixture.create();
		doReturn(removedAssignQuest).when(assignQuestService).readById(any());
		doReturn(Optional.empty()).when(questPoolRepository).findFirstByQuestParticipantIdAndQuestTypeAndSourceTypeAndProcessingStatus(any(), any(), any(), any());

		assertThatThrownBy(() -> sut.reRoll(removedAssignQuest.participantId(), removedAssignQuest.getId()))
			.isExactlyInstanceOf(QuestPoolNotFoundException.class);
	}
}
