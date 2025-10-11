package com.gomo.app.core.quest.domain.service;

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

import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.core.quest.exception.AssignQuestNotFoundException;
import com.gomo.app.core.quest.exception.code.AssignQuestErrorCode;
import com.gomo.app.core.quest.fixture.AssignQuestFixture;
import com.gomo.app.core.quest.fixture.QuestFixture;

@DisplayName("[Domain unit]: 할당 퀘스트 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class AssignQuestServiceTest {

	@InjectMocks
	private AssignQuestService sut;

	@Mock
	private AssignQuestRepository assignQuestRepository;

	@DisplayName("할당 퀘스트를 생성한다.")
	@Test
	void create_assign_quest() {
		AssignQuest assignQuest = AssignQuestFixture.create();

		doReturn(4).when(assignQuestRepository).findMaxDisplayOrderOfParticipatingQuest(any(), any(), any(), any());
		doReturn(assignQuest).when(assignQuestRepository).save(any());

		AssignQuest actual = sut.create(UUID.randomUUID(), QuestFixture.create());

		assertThat(actual.getId()).isEqualTo(assignQuest.getId());
	}

	@DisplayName("새로 생성된 할당 퀘스트의 정렬 순서는 현재 참여중인 퀘스트의 마지막 번호 + 1이다.")
	@Test
	void create_assign_quest_with_display_order() {
		doReturn(4).when(assignQuestRepository).findMaxDisplayOrderOfParticipatingQuest(any(), any(), any(), any());
		doReturn(AssignQuestFixture.create(4 + 1)).when(assignQuestRepository).save(any());

		AssignQuest actual = sut.create(UUID.randomUUID(), QuestFixture.create());

		assertThat(actual.getDisplayOrder().getDisplayOrder()).isEqualTo(4 + 1);
	}

	@DisplayName("할당 퀘스트를 조회한다.")
	@Test
	void find_assign_quest() {
		AssignQuest assignQuest = AssignQuestFixture.create();
		doReturn(Optional.of(assignQuest)).when(assignQuestRepository).findById(any());

		AssignQuest actual = sut.find(assignQuest.getId());

		assertThat(actual).isEqualTo(assignQuest);
	}

	@DisplayName("존재하지 않는 할당 퀘스트를 조회한다.")
	@Test
	void find_nonexistent_assign_quest() {
		doReturn(Optional.empty()).when(assignQuestRepository).findById(any());

		assertThatThrownBy(() -> sut.find(UUID.randomUUID()))
			.isInstanceOf(AssignQuestNotFoundException.class)
			.hasMessageContaining(AssignQuestErrorCode.NOT_FOUND.getMessage());
	}
}
