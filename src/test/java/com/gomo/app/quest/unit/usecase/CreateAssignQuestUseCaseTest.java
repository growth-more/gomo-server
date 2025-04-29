package com.gomo.app.quest.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.quest.application.CreateAssignQuestUseCase;
import com.gomo.app.quest.common.fixture.AssignQuestFixture;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.domain.service.AssignQuestService;
import com.gomo.app.quest.presentation.request.CreateAssignQuestRequest;
import com.gomo.app.quest.presentation.response.CreateAssignQuestResponse;

@DisplayName("[Application unit]: 할당 퀘스트 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class CreateAssignQuestUseCaseTest {

	@InjectMocks
	private CreateAssignQuestUseCase sut;

	@Mock
	private MemberService memberService;

	@Mock
	private AssignQuestService assignQuestService;

	@Mock
	private AssignQuestRepository assignQuestRepository;

	@DisplayName("할당 퀘스트를 생성한다.")
	@Test
	void create_assign_quest() {
		AssignQuest assignQuest = AssignQuestFixture.assignQuest();
		Member member = MemberFixture.member(5, 5, 5);
		doReturn(member).when(memberService).find(any());
		doReturn(assignQuest).when(assignQuestService).create(any(), any());
		doReturn(4L).when(assignQuestRepository).countParticipatingQuestByQuestType(any(), any(), any(), any());

		CreateAssignQuestResponse actual = sut.create(UUID.randomUUID(), createRequest());

		assertThat(actual.getId()).isEqualTo(assignQuest.getId().getId());
	}

	@DisplayName("할당량을 초과하면 할당 퀘스트를 생성할 수 없다.")
	@Test
	void check_quest_quota() {
		Member member = MemberFixture.member(5, 5, 5);
		doReturn(member).when(memberService).find(any());
		doReturn(5L).when(assignQuestRepository).countParticipatingQuestByQuestType(any(), any(), any(), any());

		assertThatThrownBy(() -> sut.create(UUID.randomUUID(), createRequest()));
	}

	private static @NotNull CreateAssignQuestRequest createRequest() {
		return CreateAssignQuestRequest.of(UUID.randomUUID(), "subject name", QuestType.DAILY, "quest content");
	}
}
