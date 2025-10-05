package com.gomo.app.core.member.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.application.port.dto.QuestPropertyDto;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.core.member.fixture.MemberFixture;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application unit]: 퀘스트 설정 조회 테스트")
public class ReadQuestPropertyUseCaseTest {

	@InjectMocks
	ReadQuestPropertyUseCase sut;

	@Mock
	private MemberService memberService;

	@DisplayName("회원의 퀘스트 설정 정보를 조회한다.")
	@Test
	void find_quest_property_successfully() {
		Member member = MemberFixture.member(3);
		QuestPropertyDto expected = QuestPropertyDto.from(member.getQuestProperty());
		doReturn(member).when(memberService).find(member.getId());

		QuestPropertyDto actual = sut.find(member.id());

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}
}
