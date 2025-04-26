package com.gomo.app.member.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gomo.app.common.util.JwtUtil;
import com.gomo.app.member.application.OAuthUseCase;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.ActivateStatus;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.model.OAuthUserInfo;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.exception.ActivateStatusException;
import com.gomo.app.member.exception.code.ActivateStatusErrorCode;
import com.gomo.app.member.infrastructure.JwtSessionRedisService;
import com.gomo.app.member.infrastructure.oauth.OAuthProvider;
import com.gomo.app.member.infrastructure.oauth.OAuthProviderFactory;
import com.gomo.app.member.presentation.response.LoginMemberResponse;

@DisplayName("[Application Unit]: OAuth 로그인 및 유저 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class OAuthUseCaseTest {
	@InjectMocks
	OAuthUseCase sut;

	@Mock
	OAuthProviderFactory providerFactory;

	@Mock
	OAuthProvider oAuthProvider;

	@Mock
	MemberRepository memberRepository;

	@Mock
	JwtSessionRedisService redisService;

	@Mock
	JwtUtil jwtUtil;

	@Mock
	private PasswordService passwordService;

	private final ObjectMapper objectMapper = new ObjectMapper();
	private final String GOOGLE_PROVIDER = "GOOGLE";
	private final String OAUTH_CODE = "google_auth_code";
	private final String EMAIL = "test@google.com";
	private final String NAME = "testname";
	private final String JWT_ACCESS_TOKEN = "jwt_access_token";
	private final String JWT_REFRESH_TOKEN = "jwt_refresh_token";
	private final long REFRESH_TOKEN_EXPIRATION_TIME = 2592000000L;

	@BeforeEach
	void setUp() {

		doReturn(oAuthProvider).when(providerFactory).getProvider(GOOGLE_PROVIDER);

		doReturn(OAuthUserInfo.builder()
			.email(EMAIL)
			.name(NAME)
			.providerId("google-provider-id")
			.build()
		).when(oAuthProvider).authenticate(OAUTH_CODE);
	}

	@DisplayName("OAuth를 이용하여 회원가입에 성공한다.")
	@Test
	public void signup_with_oauth() {
		doReturn(Optional.empty()).when(memberRepository).findByEmail(any(Email.class));

		Member member = MemberFixture.member(passwordService);
		doReturn(member).when(memberRepository).save(any(Member.class));

		doReturn(JWT_ACCESS_TOKEN).when(jwtUtil).generateAccessToken(any(MemberId.class));
		doReturn(JWT_REFRESH_TOKEN).when(jwtUtil).generateRefreshToken(any(MemberId.class));
		doReturn(REFRESH_TOKEN_EXPIRATION_TIME).when(jwtUtil).extractExpirationTime(JWT_REFRESH_TOKEN);

		LoginMemberResponse actual = sut.login(GOOGLE_PROVIDER, OAUTH_CODE);

		assertThat(actual.getAccessToken()).isEqualTo(JWT_ACCESS_TOKEN);
		assertThat(actual.getRefreshToken()).isEqualTo(JWT_REFRESH_TOKEN);
		assertThat(actual.getExpiresIn()).isEqualTo(REFRESH_TOKEN_EXPIRATION_TIME);
	}

	@DisplayName("OAuth를 이용하여 회원가입 및 로그인에 성공한다.")
	@Test
	public void login_with_oauth() {
		Member member = MemberFixture.member(passwordService);
		doReturn(Optional.of(member)).when(memberRepository).findByEmail(any(Email.class));

		doReturn(JWT_ACCESS_TOKEN).when(jwtUtil).generateAccessToken(any(MemberId.class));
		doReturn(JWT_REFRESH_TOKEN).when(jwtUtil).generateRefreshToken(any(MemberId.class));
		doReturn(REFRESH_TOKEN_EXPIRATION_TIME).when(jwtUtil).extractExpirationTime(JWT_REFRESH_TOKEN);

		LoginMemberResponse actual = sut.login(GOOGLE_PROVIDER, OAUTH_CODE);

		assertThat(actual.getAccessToken()).isEqualTo(JWT_ACCESS_TOKEN);
		assertThat(actual.getRefreshToken()).isEqualTo(JWT_REFRESH_TOKEN);
		assertThat(actual.getExpiresIn()).isEqualTo(REFRESH_TOKEN_EXPIRATION_TIME);
	}

	@DisplayName("Block 된 이메일로 OAuth로그인을 시도할 경우, 실패한다.")
	@Test
	public void oauth_login_with_blocked_email() {
		Member member = MemberFixture.member(ActivateStatus.BLOCKED, passwordService);
		doReturn(Optional.of(member)).when(memberRepository).findByEmail(any(Email.class));

		assertThatThrownBy(() -> sut.login(GOOGLE_PROVIDER, OAUTH_CODE))
			.isInstanceOf(ActivateStatusException.class)
			.hasMessageContaining(ActivateStatusErrorCode.BLOCKED.getMessage());
	}

	@DisplayName("탈퇴 처리된 이메일로 OAuth로그인을 시도할 경우, 실패한다.")
	@Test
	public void oauth_login_with_deleted_email() {
		Member member = MemberFixture.member(ActivateStatus.DELETED, passwordService);
		doReturn(Optional.of(member)).when(memberRepository).findByEmail(any(Email.class));

		assertThatThrownBy(() -> sut.login(GOOGLE_PROVIDER, OAUTH_CODE))
			.isInstanceOf(ActivateStatusException.class)
			.hasMessageContaining(ActivateStatusErrorCode.DELETED.getMessage());
	}
}
