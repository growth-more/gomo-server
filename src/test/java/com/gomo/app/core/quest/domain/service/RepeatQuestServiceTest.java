package com.gomo.app.core.quest.domain.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.quest.domain.model.repeat.RepeatQuest;
import com.gomo.app.core.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.core.quest.fixture.QuestFixture;
import com.gomo.app.core.quest.fixture.RepeatQuestFixture;

@DisplayName("[Domain unit]: 반복 퀘스트 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class RepeatQuestServiceTest {

	@InjectMocks
	private RepeatQuestService sut;

	@Mock
	private RepeatQuestRepository repeatQuestRepository;

	@DisplayName("반복 퀘스트를 생성한다.")
	@Test
	void create_repeat_quest() {
		RepeatQuest repeatQuest = RepeatQuestFixture.create();
		doReturn(4).when(repeatQuestRepository).findMaxDisplayOrderByQuestType(any(), any());
		doReturn(repeatQuest).when(repeatQuestRepository).save(any());

		RepeatQuest actual = sut.create(UUID.randomUUID(), QuestFixture.create());

		assertThat(actual.getId()).isEqualTo(repeatQuest.getId());
	}

	@DisplayName("새로 생성된 반복 퀘스트의 정렬 순서는 현재 등록된 반복 퀘스트의 마지막 번호 + 1이다.")
	@Test
	void create_repeat_quest_with_display_order() {
		doReturn(4).when(repeatQuestRepository).findMaxDisplayOrderByQuestType(any(), any());
		doReturn(RepeatQuestFixture.create(4 + 1)).when(repeatQuestRepository).save(any());

		RepeatQuest actual = sut.create(UUID.randomUUID(), QuestFixture.create());

		assertThat(actual.getDisplayOrder().getDisplayOrder()).isEqualTo(4 + 1);
	}
}
