package com.gomo.app.member.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.member.application.UpdateQuestPropertyUseCase;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.QuestProperty;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.presentation.request.UpdateQuestPropertyRequest;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application Unit]: 퀘스트 설정 업데이트 기능 테스트")
public class UpdateQuestPropertyUseCaseTest {

	@InjectMocks
	UpdateQuestPropertyUseCase sut;

	@Mock
	private MemberService memberService;

	@DisplayName("퀘스트 설정 업데이트 기능 테스트")
	@Test
	void update_quest_property() {
		Member member = MemberFixture.member();
		UpdateQuestPropertyRequest request = UpdateQuestPropertyRequest.of(1, 3, 5);
		QuestProperty expected = request.toDomain();

		doReturn(member).when(memberService).find(member.getId());
		sut.update(member.uuid(), request);

		assertThat(member.getQuestProperty()).usingRecursiveComparison().isEqualTo(expected);
	}
}
