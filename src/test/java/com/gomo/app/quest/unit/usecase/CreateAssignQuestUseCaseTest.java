package com.gomo.app.quest.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.quest.application.CreateAssignQuestUseCase;
import com.gomo.app.quest.common.fixture.AssignQuestFixture;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.exception.AssignQuestThresholdExceededException;
import com.gomo.app.quest.presentation.request.CreateAssignQuestRequest;
import com.gomo.app.quest.presentation.response.CreateAssignQuestResponse;

@DisplayName("[Application unit]: 할당 퀘스트 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class CreateAssignQuestUseCaseTest {

	@InjectMocks
	private CreateAssignQuestUseCase sut;

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private AssignQuestRepository assignQuestRepository;

	@DisplayName("할당 퀘스트를 생성한다.")
	@Test
	void create_assign_quest() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest();
		doReturn(4L).when(assignQuestRepository).countByQuestParticipantIdAndQuestTypeAndStartDateTimeBetween(
			any(ParticipantId.class),
			any(QuestType.class),
			any(LocalDateTime.class),
			any(LocalDateTime.class)
		);
		doReturn(Optional.of(MemberFixture.member(5))).when(memberRepository).findById(any(MemberId.class));
		doReturn(assignQuest).when(assignQuestRepository).save(any(AssignQuest.class));

		CreateAssignQuestResponse actual = sut.create(ParticipantId.of(UUID.randomUUID()), createMockRequest());

		assertThat(actual.getId()).isEqualTo(assignQuest.getId().getId());
	}

	@DisplayName("할당 퀘스트는 사용자가 지정한 개수를 초과해서 생성할 수 없다.")
	@Test
	void create_assign_quest_exceeding_quest_property() {
		doReturn(5L).when(assignQuestRepository).countByQuestParticipantIdAndQuestTypeAndStartDateTimeBetween(
			any(ParticipantId.class),
			any(QuestType.class),
			any(LocalDateTime.class),
			any(LocalDateTime.class)
		);
		doReturn(Optional.of(MemberFixture.member(5))).when(memberRepository).findById(any(MemberId.class));

		assertThatThrownBy(() -> sut.create(ParticipantId.of(UUID.randomUUID()), createMockRequest()))
			.isInstanceOf(AssignQuestThresholdExceededException.class)
			.hasMessageContaining("Assign quest threshold exceeded");
	}

	private static @NotNull CreateAssignQuestRequest createMockRequest() {
		return CreateAssignQuestRequest.of(UUID.randomUUID(), "subject name", QuestType.DAILY, "quest content");
	}
}
