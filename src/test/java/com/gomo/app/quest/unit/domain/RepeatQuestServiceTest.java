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

import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.quest.common.fixture.QuestFixture;
import com.gomo.app.quest.common.fixture.RepeatQuestFixture;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.quest.domain.service.RepeatQuestService;
import com.gomo.app.quest.exception.RepeatQuestThresholdExceededException;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Domain unit]: 반복 퀘스트 생성 테스트")
public class RepeatQuestServiceTest {

	@InjectMocks
	RepeatQuestService sut;

	@Mock
	MemberRepository memberRepository;

	@Mock
	RepeatQuestRepository repeatQuestRepository;

	@DisplayName("반복 퀘스트를 생성한다.")
	@Test
	void create_repeat_quest() {
		RepeatQuest repeatQuest = RepeatQuestFixture.repeatQuest();

		doReturn(4L).when(repeatQuestRepository).countByQuestParticipantIdAndQuestType(any(), any());
		doReturn(Optional.of(MemberFixture.member(5))).when(memberRepository).findById(any());
		doReturn(4).when(repeatQuestRepository).findMaxDisplayOrderByQuestType(any(), any());
		doReturn(repeatQuest).when(repeatQuestRepository).save(any());

		RepeatQuest actual = sut.create(ParticipantId.of(UUID.randomUUID()), QuestFixture.quest());

		assertThat(actual.getId()).isEqualTo(repeatQuest.getId());
	}

	@DisplayName("새로 생성된 반복 퀘스트의 정렬 순서는 현재 등록된 반복 퀘스트의 마지막 번호 + 1이다.")
	@Test
	void create_repeat_quest_with_display_order() {
		int maxDisplayOrder = 4;

		doReturn(4L).when(repeatQuestRepository).countByQuestParticipantIdAndQuestType(any(), any());
		doReturn(Optional.of(MemberFixture.member(5))).when(memberRepository).findById(any());
		doReturn(maxDisplayOrder).when(repeatQuestRepository).findMaxDisplayOrderByQuestType(any(), any());
		doReturn(RepeatQuestFixture.repeatQuest(maxDisplayOrder + 1)).when(repeatQuestRepository).save(any());

		RepeatQuest actual = sut.create(ParticipantId.of(UUID.randomUUID()), QuestFixture.quest());

		assertThat(actual.getDisplayOrder().getDisplayOrder()).isEqualTo(maxDisplayOrder + 1);
	}

	@DisplayName("반복 퀘스트는 사용자가 지정한 개수를 초과해서 생성할 수 없다.")
	@Test
	void create_repeat_quest_exceeding_quest_property() {
		doReturn(5L).when(repeatQuestRepository).countByQuestParticipantIdAndQuestType(any(), any());
		doReturn(Optional.of(MemberFixture.member(5))).when(memberRepository).findById(any());

		assertThatThrownBy(() -> sut.create(ParticipantId.of(UUID.randomUUID()), QuestFixture.quest()))
			.isInstanceOf(RepeatQuestThresholdExceededException.class)
			.hasMessageContaining("Repeat quest threshold exceeded");
	}
}
