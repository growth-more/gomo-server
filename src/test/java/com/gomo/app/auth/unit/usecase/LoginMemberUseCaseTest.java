package com.gomo.app.auth.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.support.auth.application.AuthTokenGenerator;
import com.gomo.app.support.auth.application.LoginMemberUseCase;
import com.gomo.app.support.auth.domain.model.AuthToken;
import com.gomo.app.support.auth.presentation.response.AuthTokenResponse;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.core.member.domain.service.PasswordService;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application Unit]: 사용자 로그인 테스트")
public class LoginMemberUseCaseTest {

	@InjectMocks
	LoginMemberUseCase sut;

	@Mock
	private MemberService memberService;

	@Mock
	private PasswordService passwordService;

	@Mock
	private AuthTokenGenerator authTokenGenerator;

	@DisplayName("사용자가 로그인에 성공한다.")
	@Test
	void login_success() {
		Member member = MemberFixture.member();
		AuthToken authToken = AuthToken.of("access", "refresh");
		AuthTokenResponse expected = AuthTokenResponse.of(member.uuid(), authToken, 1L);

		doReturn(member).when(memberService).findByEmail(any(Email.class));
		doNothing().when(memberService).checkActivated(member);

		doReturn(true).when(passwordService).matches(anyString(), anyString());
		doReturn(authToken).when(authTokenGenerator).generate(any());
		doReturn(1L).when(authTokenGenerator).getRefreshTokenExpirationTime(anyString());

		AuthTokenResponse actual = sut.login(member.getEmail().getEmail(), member.getPassword().getPassword());

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}
}
