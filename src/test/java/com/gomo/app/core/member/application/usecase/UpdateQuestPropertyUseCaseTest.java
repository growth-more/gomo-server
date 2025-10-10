package com.gomo.app.core.member.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.application.port.command.UpdateQuestPropertyCommand;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.model.QuestProperty;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.core.member.fixture.MemberFixture;

@DisplayName("[Application Unit]: 퀘스트 설정 업데이트 기능 테스트")
@ExtendWith(MockitoExtension.class)
public class UpdateQuestPropertyUseCaseTest {

	@InjectMocks
	private UpdateQuestPropertyUseCase sut;

	@Mock
	private MemberService memberService;

	@DisplayName("퀘스트 설정 업데이트 기능 테스트")
	@Test
	void update_quest_property() {
		Member member = MemberFixture.create();
		UpdateQuestPropertyCommand command = UpdateQuestPropertyCommand.of(member.id(), 1, 3, 5);
		QuestProperty expected = command.toDomain();
		doReturn(member).when(memberService).find(member.getId());

		sut.update(command);

		assertThat(member.getQuestProperty()).usingRecursiveComparison().isEqualTo(expected);
	}
}
