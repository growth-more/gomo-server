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
import com.gomo.app.support.auth.application.RefreshTokenUseCase;
import com.gomo.app.support.auth.domain.model.AuthToken;
import com.gomo.app.support.auth.domain.repository.AuthTokenRepository;
import com.gomo.app.support.auth.presentation.response.AuthTokenResponse;
import com.gomo.app.common.util.JwtUtil;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.exception.MemberAuthenticationFailedException;
import com.gomo.app.core.member.exception.code.MemberErrorCode;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application Unit]: Refresh 토큰 재발급 테스트")
public class RefreshTokenUseCaseTest {

	@InjectMocks
	RefreshTokenUseCase sut;

	@Mock
	private AuthTokenGenerator authTokenGenerator;

	@Mock
	private JwtUtil jwtUtil;

	@Mock
	private AuthTokenRepository authTokenRepository;

	private static final String REFRESH_TOKEN = "REFRESH_TOKEN";
	private static final String REFRESH_TOKEN_WRONG = "WRONG_TOKEN";

	@DisplayName("Refresh 토큰 재발급에 성공한다.")
	@Test
	void renew_refresh_token_successfully() {
		Member member = MemberFixture.member();
		AuthToken authToken = AuthToken.of("access", "refresh");
		AuthTokenResponse expected = AuthTokenResponse.of(member.uuid(), authToken, 1L);

		doReturn(member.uuid().toString()).when(jwtUtil).extractMemberId(anyString());
		doReturn(REFRESH_TOKEN).when(authTokenRepository).getRefreshToken(member.uuid());
		doReturn(authToken).when(authTokenGenerator).generate(member.uuid());
		doReturn(1L).when(authTokenGenerator).getRefreshTokenExpirationTime(anyString());

		AuthTokenResponse actual = sut.refresh(REFRESH_TOKEN);

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}

	@DisplayName("Refresh 토큰이 null로 들어올 경우 재발급에 실패한다.")
	@Test
	void renew_refresh_token_with_null() {
		assertThatThrownBy(() -> sut.refresh(null))
			.isInstanceOf(MemberAuthenticationFailedException.class)
			.hasMessageContaining(MemberErrorCode.AUTHENTICATION_FAILED.getMessage());
	}

	@DisplayName("Refresh 토큰이 저장된 값과 다를 경우 재발급에 실패한다.")
	@Test
	void renew_refresh_token_with_wrong_token() {
		Member member = MemberFixture.member();
		doReturn(member.uuid().toString()).when(jwtUtil).extractMemberId(anyString());
		doReturn(REFRESH_TOKEN_WRONG).when(authTokenRepository).getRefreshToken(member.uuid());

		assertThatThrownBy(() -> sut.refresh(REFRESH_TOKEN))
			.isInstanceOf(MemberAuthenticationFailedException.class)
			.hasMessageContaining(MemberErrorCode.AUTHENTICATION_FAILED.getMessage());
	}
}
