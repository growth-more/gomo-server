package com.gomo.app.core.member.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.application.port.VerifyPasswordPortOut;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.core.member.fixture.MemberFixture;

@DisplayName("[Application unit]: 회원 로그인 테스트")
@ExtendWith(MockitoExtension.class)
class LoginMemberUseCaseTest {

	@InjectMocks
	private LoginMemberUseCase sut;

	@Mock
	private MemberService memberService;

	@Mock
	private VerifyPasswordPortOut verifyPasswordPortOut;

	@DisplayName("회원 정보를 확인하고, 로그인 날짜를 갱신한다.")
	@Test
	void login_member() {
		Member member = MemberFixture.create();
		doReturn(member).when(memberService).findByEmail(any());
		doReturn(true).when(verifyPasswordPortOut).matches(any(), any());
		UUID actual = sut.authenticate(member.email(), member.password());
		assertThat(actual).isEqualTo(member.id());
	}
}
