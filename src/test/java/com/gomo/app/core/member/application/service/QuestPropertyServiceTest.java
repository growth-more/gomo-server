package com.gomo.app.core.member.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.application.port.command.UpdateQuestPropertyCommand;
import com.gomo.app.core.member.application.port.dto.QuestPropertyDto;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.model.QuestProperty;
import com.gomo.app.core.member.fixture.MemberFixture;

@DisplayName("[Application Unit]: 퀘스트 설정 업데이트 기능 테스트")
@ExtendWith(MockitoExtension.class)
public class QuestPropertyServiceTest {

	@InjectMocks
	private QuestPropertyService sut;

	@Mock
	private MemberService memberService;

	@DisplayName("회원의 퀘스트 설정 정보를 조회한다.")
	@Test
	void read_quest_property_successfully() {
		Member member = MemberFixture.create(3);
		QuestPropertyDto expected = QuestPropertyDto.from(member.getQuestProperty());
		doReturn(member).when(memberService).findById(member.getId());

		QuestPropertyDto actual = sut.read(member.getId());

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}

	@DisplayName("회원의 퀘스트 설정 정보를 수정한다.")
	@Test
	void update_quest_property() {
		Member member = MemberFixture.create();
		UpdateQuestPropertyCommand command = UpdateQuestPropertyCommand.of(member.getId(), 1, 3, 5);
		QuestProperty expected = command.toDomain();
		doReturn(member).when(memberService).findById(member.getId());

		sut.update(command);

		assertThat(member.getQuestProperty()).usingRecursiveComparison().isEqualTo(expected);
	}
}
