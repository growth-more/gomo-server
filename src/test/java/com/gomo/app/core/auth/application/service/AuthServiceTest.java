package com.gomo.app.core.auth.application.service;

import static com.gomo.app.core.auth.domain.exception.AuthErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.auth.application.port.command.CreatePrincipalCommand;
import com.gomo.app.core.auth.application.port.dto.AuthTokenDto;
import com.gomo.app.core.auth.application.port.out.JwtVerifier;
import com.gomo.app.core.auth.application.port.out.PrincipalCreator;
import com.gomo.app.core.auth.application.port.out.PrincipalLoginProcessor;
import com.gomo.app.core.auth.domain.exception.AuthenticationFailException;
import com.gomo.app.core.auth.domain.model.AuthToken;
import com.gomo.app.core.member.domain.model.LoginProvider;

@DisplayName("[Application Unit]: 사용자 로그인 테스트")
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

	@InjectMocks
	private AuthService sut;

	@Mock
	private PrincipalCreator principalCreator;

	@Mock
	private PrincipalLoginProcessor principalLoginProcessor;

	@Mock
	private JwtVerifier jwtVerifier;

	@Mock
	private AuthTokenService authTokenService;

	@DisplayName("이메일로 회원 가입한다.")
	@Test
	void email_signup() {
		CreatePrincipalCommand command = CreatePrincipalCommand.of("email@email.com", "Gomo123@", "handle", "name", "motto", LoginProvider.EMAIL.name(), "token");
		UUID principalId = UUID.randomUUID();
		doReturn(true).when(jwtVerifier).verify(anyString());
		doReturn(principalId).when(principalCreator).create(any());

		UUID actual = sut.signup(command);

		assertThat(actual).isEqualTo(principalId);
	}

	@DisplayName("OAuth로 회원 가입한다.")
	@ParameterizedTest
	@EnumSource(value = LoginProvider.class, names = {"NAVER", "GOOGLE", "KAKAO"})
	void oauth_signup(LoginProvider loginProvider) {
		CreatePrincipalCommand command = CreatePrincipalCommand.of("email@email.com", "Gomo123@", "handle", "name", "motto", loginProvider.name(), "token");
		UUID principalId = UUID.randomUUID();
		doReturn(principalId).when(principalCreator).create(any());

		UUID actual = sut.signup(command);

		assertThat(actual).isEqualTo(principalId);
	}

	@DisplayName("유효하지 않은 검증된 이메일 토큰으로 가입한다.")
	@Test
	void signup_with_invalid_auth_token() {
		CreatePrincipalCommand command = CreatePrincipalCommand.of("email@email.com", "Gomo123@", "handle", "name", "motto", LoginProvider.EMAIL.name(), "token");
		doReturn(false).when(jwtVerifier).verify(anyString());

		assertThatThrownBy(() -> sut.signup(command))
			.isInstanceOf(AuthenticationFailException.class)
			.hasMessageContaining(INVALID_VERIFIED_EMAIL_TOKEN.getMessage());
	}

	@DisplayName("사용자가 로그인한다.")
	@Test
	void login() {
		UUID memberId = UUID.randomUUID();
		AuthToken authToken = AuthToken.of("access", "refresh");
		AuthTokenDto expected = AuthTokenDto.of(memberId, authToken.getAccessToken(), authToken.getRefreshToken(), 1L);
		doReturn(memberId).when(principalLoginProcessor).login(anyString(), anyString());
		doReturn(authToken).when(authTokenService).create(any());
		doReturn(1L).when(jwtVerifier).extractExpirationTime(anyString());

		AuthTokenDto actual = sut.login("test@gmail.com", "Gomo123@");

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}
}
