package com.gomo.app.quest.unit.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.quest.common.fixture.AssignQuestFixture;
import com.gomo.app.quest.common.fixture.QuestFixture;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.domain.service.AssignQuestService;
import com.gomo.app.quest.exception.AssignQuestNotFoundException;
import com.gomo.app.quest.exception.code.AssignQuestErrorCode;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Domain unit]: 할당 퀘스트 생성 테스트")
public class AssignQuestServiceTest {

	@InjectMocks
	AssignQuestService sut;

	@Mock
	AssignQuestRepository assignQuestRepository;

	@DisplayName("할당 퀘스트를 생성한다.")
	@Test
	void create_assign_quest() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest();

		doReturn(4).when(assignQuestRepository).findMaxDisplayOrderOfParticipatingQuest(any(), any(), any(), any());
		doReturn(assignQuest).when(assignQuestRepository).save(any());

		AssignQuest actual = sut.create(ParticipantId.of(UUID.randomUUID()), QuestFixture.quest());

		assertThat(actual.getId()).isEqualTo(assignQuest.getId());
	}

	@DisplayName("새로 생성된 할당 퀘스트의 정렬 순서는 현재 참여중인 퀘스트의 마지막 번호 + 1이다.")
	@Test
	void create_assign_quest_with_display_order() {
		doReturn(4).when(assignQuestRepository).findMaxDisplayOrderOfParticipatingQuest(any(), any(), any(), any());
		doReturn(AssignQuestFixture.assignQuest(4 + 1)).when(assignQuestRepository).save(any());

		AssignQuest actual = sut.create(ParticipantId.of(UUID.randomUUID()), QuestFixture.quest());

		assertThat(actual.getDisplayOrder().getDisplayOrder()).isEqualTo(4 + 1);
	}

	@DisplayName("할당 퀘스트를 조회한다.")
	@Test
	void find_assign_quest() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest();
		doReturn(Optional.of(assignQuest)).when(assignQuestRepository).findById(any());

		AssignQuest actual = sut.find(assignQuest.getId());

		assertThat(actual).isEqualTo(assignQuest);
	}

	@DisplayName("존재하지 않는 할당 퀘스트를 조회한다.")
	@Test
	void find_nonexistent_assign_quest() {
		doReturn(Optional.empty()).when(assignQuestRepository).findById(any());

		assertThatThrownBy(() -> sut.find(AssignQuestId.of(UUID.randomUUID())))
			.isInstanceOf(AssignQuestNotFoundException.class)
			.hasMessageContaining(AssignQuestErrorCode.NOT_FOUND.getMessage());
	}
}
