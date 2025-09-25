package com.gomo.app.auth.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.common.jwt.port.VerifyJwtPortIn;
import com.gomo.app.core.member.common.fixture.MemberFixture;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.exception.MemberAuthenticationFailedException;
import com.gomo.app.core.member.exception.code.MemberErrorCode;
import com.gomo.app.support.auth.application.CreateAuthTokenUseCase;
import com.gomo.app.support.auth.application.UpdateRefreshTokenUseCase;
import com.gomo.app.support.auth.domain.model.AuthToken;
import com.gomo.app.support.auth.domain.repository.AuthTokenRepository;
import com.gomo.app.support.auth.presentation.response.AuthTokenResponse;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application Unit]: Refresh 토큰 재발급 테스트")
public class UpdateRefreshTokenUseCaseTest {

	@InjectMocks
	UpdateRefreshTokenUseCase sut;

	@Mock
	private CreateAuthTokenUseCase createAuthTokenUseCase;

	@Mock
	private VerifyJwtPortIn verifyJwtPortIn;

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

		doReturn(member.uuid().toString()).when(verifyJwtPortIn).extractSubject(anyString());
		doReturn(REFRESH_TOKEN).when(authTokenRepository).getRefreshToken(member.uuid());
		doReturn(authToken).when(createAuthTokenUseCase).create(member.uuid());
		doReturn(1L).when(verifyJwtPortIn).extractExpirationTime(anyString());

		AuthTokenResponse actual = sut.update(REFRESH_TOKEN);

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}

	@DisplayName("Refresh 토큰이 null로 들어올 경우 재발급에 실패한다.")
	@Test
	void renew_refresh_token_with_null() {
		assertThatThrownBy(() -> sut.update(null))
			.isInstanceOf(MemberAuthenticationFailedException.class)
			.hasMessageContaining(MemberErrorCode.AUTHENTICATION_FAILED.getMessage());
	}

	@DisplayName("Refresh 토큰이 저장된 값과 다를 경우 재발급에 실패한다.")
	@Test
	void renew_refresh_token_with_wrong_token() {
		Member member = MemberFixture.member();
		doReturn(member.uuid().toString()).when(verifyJwtPortIn).extractSubject(anyString());
		doReturn(REFRESH_TOKEN_WRONG).when(authTokenRepository).getRefreshToken(member.uuid());

		assertThatThrownBy(() -> sut.update(REFRESH_TOKEN))
			.isInstanceOf(MemberAuthenticationFailedException.class)
			.hasMessageContaining(MemberErrorCode.AUTHENTICATION_FAILED.getMessage());
	}
}
