package com.gomo.app.core.member.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.common.fixture.MemberFixture;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.service.MemberService;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application unit]: 멤버 수정(이름, 모토) 테스트")
public class UpdateMemberUseCaseTest {

	@InjectMocks
	UpdateMemberUseCase sut;

	@Mock
	private MemberService memberService;

	@DisplayName("회원 정보(모토, 이름)을 수정한다.")
	@Test
	void update_member_name_and_motto() {
		Member member = MemberFixture.member();
		doReturn(member).when(memberService).find(member.getId());
		sut.update(member.id(), "NEW_NAME", "NEW_MOTTO");
		assertThat(member.getName().getName()).isEqualTo("NEW_NAME");
		assertThat(member.getMotto().getMotto()).isEqualTo("NEW_MOTTO");
	}

	// TODO: <nurdy-kim> 회원 수정 로직 보완이 필요합니다.
	// @DisplayName("회원 정보(이름)을 수정한다.")
	// @Test
	// void update_member_name() {
	// 	Member member = MemberFixture.member();
	// 	UpdateMemberRequest request = UpdateMemberRequest.of("NEW_NAME2", null);
	//
	// 	doReturn(member).when(memberService).find(member.getId());
	//
	// 	sut.update(member.uuid(), request);
	//
	// 	assertThat(member.getName().getName()).isEqualTo("NEW_NAME2");
	// }
	//
	// @DisplayName("회원 정보(모토)을 수정한다.")
	// @Test
	// void update_member_motto() {
	// 	Member member = MemberFixture.member();
	// 	UpdateMemberRequest request = UpdateMemberRequest.of(null, "NEW_MOTTO_2");
	//
	// 	doReturn(member).when(memberService).find(member.getId());
	//
	// 	sut.update(member.uuid(), request);
	//
	// 	assertThat(member.getMotto().getMotto()).isEqualTo("NEW_MOTTO_2");
	// }
}
