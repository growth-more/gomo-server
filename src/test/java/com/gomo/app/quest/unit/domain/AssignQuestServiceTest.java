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
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.quest.common.fixture.AssignQuestFixture;
import com.gomo.app.quest.common.fixture.QuestFixture;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.domain.service.AssignQuestService;
import com.gomo.app.quest.exception.AssignQuestConstraintViolationException;
import com.gomo.app.quest.exception.code.AssignQuestErrorCode;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Domain unit]: 할당 퀘스트 생성 테스트")
public class AssignQuestServiceTest {

	@InjectMocks
	AssignQuestService sut;

	@Mock
	MemberRepository memberRepository;

	@Mock
	AssignQuestRepository assignQuestRepository;

	@Mock
	PasswordService passwordService;

	@DisplayName("할당 퀘스트를 생성한다.")
	@Test
	void create_assign_quest() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest();

		doReturn(4L).when(assignQuestRepository).countParticipatingQuestByQuestType(any(), any(), any(), any());
		doReturn(Optional.of(MemberFixture.member(5, passwordService))).when(memberRepository).findById(any());
		doReturn(4).when(assignQuestRepository).findMaxDisplayOrderOfParticipatingQuest(any(), any(), any(), any());
		doReturn(assignQuest).when(assignQuestRepository).save(any());

		AssignQuest actual = sut.create(ParticipantId.of(UUID.randomUUID()), QuestFixture.quest());

		assertThat(actual.getId()).isEqualTo(assignQuest.getId());
	}

	@DisplayName("새로 생성된 할당 퀘스트의 정렬 순서는 현재 참여중인 퀘스트의 마지막 번호 + 1이다.")
	@Test
	void create_assign_quest_with_display_order() {
		int maxDisplayOrder = 4;

		doReturn(4L).when(assignQuestRepository).countParticipatingQuestByQuestType(any(), any(), any(), any());
		doReturn(Optional.of(MemberFixture.member(5, passwordService))).when(memberRepository).findById(any());
		doReturn(maxDisplayOrder).when(assignQuestRepository).findMaxDisplayOrderOfParticipatingQuest(any(), any(), any(), any());
		doReturn(AssignQuestFixture.assignQuest(maxDisplayOrder + 1)).when(assignQuestRepository).save(any());

		AssignQuest actual = sut.create(ParticipantId.of(UUID.randomUUID()), QuestFixture.quest());

		assertThat(actual.getDisplayOrder().getDisplayOrder()).isEqualTo(maxDisplayOrder + 1);
	}

	@DisplayName("할당 퀘스트는 사용자가 지정한 개수를 초과해서 생성할 수 없다.")
	@Test
	void create_assign_quest_exceeding_quest_property() {
		doReturn(5L).when(assignQuestRepository).countParticipatingQuestByQuestType(any(), any(), any(), any());
		doReturn(Optional.of(MemberFixture.member(5, passwordService))).when(memberRepository).findById(any());

		assertThatThrownBy(() -> sut.create(ParticipantId.of(UUID.randomUUID()), QuestFixture.quest()))
			.isInstanceOf(AssignQuestConstraintViolationException.class)
			.hasMessageContaining(AssignQuestErrorCode.THRESHOLD_EXCEEDED.getMessage());
	}
}
