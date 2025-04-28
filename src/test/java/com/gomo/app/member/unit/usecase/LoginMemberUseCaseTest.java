package com.gomo.app.member.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.common.util.JwtUtil;
import com.gomo.app.member.application.LoginMemberUseCase;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.ActivateStatus;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.exception.ActivateStatusException;
import com.gomo.app.member.exception.code.ActivateStatusErrorCode;
import com.gomo.app.member.infrastructure.JwtSessionRedisService;
import com.gomo.app.member.presentation.response.LoginMemberResponse;

@DisplayName("[Application Unit]: 이메일 로그인 테스트")
@ExtendWith(MockitoExtension.class)
public class LoginMemberUseCaseTest {
	@InjectMocks
	private LoginMemberUseCase sut;

	@Mock
	private MemberService memberService;

	@Mock
	private PasswordService passwordService;

	@Mock
	private JwtSessionRedisService jwtSessionRedisService;

	@Mock
	private JwtUtil jwtUtil;

	private static final String EMAIL = "test@naver.com";
	private static final String PASSWORD = "Test123!";
	private static final String ACCESS_TOKEN = "access_token";
	private static final String REFRESH_TOKEN = "refresh_token";
	private static final long EXP_TIME = 2592000000L;

	@DisplayName("이메일 로그인에 성공한다.")
	@Test
	void login_successfully() {
		Member member = MemberFixture.member();
		LoginMemberResponse expected = LoginMemberResponse.of(member.uuid(), ACCESS_TOKEN, REFRESH_TOKEN, EXP_TIME);

		doReturn(member).when(memberService).findByEmail(any(Email.class));
		doReturn(true).when(passwordService).matches(PASSWORD, member.getPassword().getPassword());
		doReturn(ACCESS_TOKEN).when(jwtUtil).generateAccessToken(member.uuid());
		doReturn(REFRESH_TOKEN).when(jwtUtil).generateRefreshToken(member.uuid());
		doReturn(EXP_TIME).when(jwtUtil).extractExpirationTime(REFRESH_TOKEN);

		doNothing().when(jwtSessionRedisService).setRefreshToken(any(), any());

		LoginMemberResponse actual = sut.login(EMAIL, PASSWORD);

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}

	@DisplayName("정지된 이메일 계정으로 로그인 시, 로그인에 실패한다.")
	@Test
	void email_login_with_blocked_email() {
		doReturn(MemberFixture.member(ActivateStatus.BLOCKED)).when(memberService).findByEmail(any(Email.class));

		assertThatThrownBy(() -> sut.login(EMAIL, PASSWORD))
			.isInstanceOf(ActivateStatusException.class)
			.hasMessageContaining(ActivateStatusErrorCode.BLOCKED.getMessage());
	}

	@DisplayName("탈퇴한 이메일 계정으로 로그인 시, 로그인에 실패한다.")
	@Test
	void email_login_with_deleted_email() {
		doReturn(MemberFixture.member(ActivateStatus.DELETED)).when(memberService).findByEmail(any(Email.class));

		assertThatThrownBy(() -> sut.login(EMAIL, PASSWORD))
			.isInstanceOf(ActivateStatusException.class)
			.hasMessageContaining(ActivateStatusErrorCode.DELETED.getMessage());
	}
}
