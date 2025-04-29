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
import com.gomo.app.quest.application.CreateRepeatQuestUseCase;
import com.gomo.app.quest.common.fixture.RepeatQuestFixture;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.quest.domain.service.RepeatQuestService;
import com.gomo.app.quest.presentation.request.CreateRepeatQuestRequest;
import com.gomo.app.quest.presentation.response.CreateRepeatQuestResponse;

@DisplayName("[Application unit]: 반복 퀘스트 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class CreateRepeatQuestUseCaseTest {

	@InjectMocks
	private CreateRepeatQuestUseCase sut;

	@Mock
	private MemberService memberService;

	@Mock
	private RepeatQuestService repeatQuestService;

	@Mock
	private RepeatQuestRepository repeatQuestRepository;

	@DisplayName("반복 퀘스트를 생성한다.")
	@Test
	void create_repeat_quest() {
		RepeatQuest repeatQuest = RepeatQuestFixture.repeatQuest();
		Member member = MemberFixture.member(5, 5, 5);
		doReturn(member).when(memberService).find(any());
		doReturn(repeatQuest).when(repeatQuestService).create(any(), any());
		doReturn(4L).when(repeatQuestRepository).countByQuestParticipantIdAndQuestType(any(), any());

		CreateRepeatQuestResponse actual = sut.create(UUID.randomUUID(), createRequest());

		assertThat(actual.getId()).isEqualTo(repeatQuest.getId().getId());
	}

	@DisplayName("할당량을 초과하면 반복 퀘스트를 생성할 수 없다.")
	@Test
	void check_quest_quota() {
		Member member = MemberFixture.member(5, 5, 5);
		doReturn(member).when(memberService).find(any());
		doReturn(5L).when(repeatQuestRepository).countByQuestParticipantIdAndQuestType(any(), any());

		assertThatThrownBy(() -> sut.create(UUID.randomUUID(), createRequest()));
	}

	private static @NotNull CreateRepeatQuestRequest createRequest() {
		return CreateRepeatQuestRequest.of(UUID.randomUUID(), "subject name", QuestType.DAILY, "quest content");
	}
}
