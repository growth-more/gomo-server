package com.gomo.app.support.auth.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.common.jwt.port.VerifyJwtPortIn;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.fixture.MemberFixture;
import com.gomo.app.support.auth.application.port.dto.AuthTokenDto;
import com.gomo.app.support.auth.domain.model.AuthToken;
import com.gomo.app.support.auth.domain.repository.AuthTokenRepository;
import com.gomo.app.support.auth.exception.AuthErrorCode;
import com.gomo.app.support.auth.exception.AuthenticationFailException;

@DisplayName("[Application Unit]: Refresh 토큰 재발급 테스트")
@ExtendWith(MockitoExtension.class)
public class UpdateRefreshTokenUseCaseTest {

	@InjectMocks
	private UpdateRefreshTokenUseCase sut;

	@Mock
	private CreateAuthTokenInternalService createAuthTokenInternalService;

	@Mock
	private VerifyJwtPortIn verifyJwtPortIn;

	@Mock
	private AuthTokenRepository authTokenRepository;

	private static final String REFRESH_TOKEN = "REFRESH_TOKEN";
	private static final String REFRESH_TOKEN_WRONG = "WRONG_TOKEN";

	@DisplayName("Refresh 토큰 재발급에 성공한다.")
	@Test
	void renew_refresh_token_successfully() {
		Member member = MemberFixture.create();
		AuthToken authToken = AuthToken.of("access", "refresh");
		AuthTokenDto expected = AuthTokenDto.of(member.id(), authToken.getAccessToken(), authToken.getRefreshToken(), 1L);

		doReturn(member.id().toString()).when(verifyJwtPortIn).extractSubject(anyString());
		doReturn(REFRESH_TOKEN).when(authTokenRepository).getRefreshToken(member.id());
		doReturn(authToken).when(createAuthTokenInternalService).create(member.id());
		doReturn(1L).when(verifyJwtPortIn).extractExpirationTime(anyString());

		AuthTokenDto actual = sut.update(REFRESH_TOKEN);

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}

	@DisplayName("Refresh 토큰이 null로 들어올 경우 재발급에 실패한다.")
	@Test
	void renew_refresh_token_with_null() {
		assertThatThrownBy(() -> sut.update(null))
			.isInstanceOf(AuthenticationFailException.class)
			.hasMessageContaining(AuthErrorCode.MISSING_REFRESH_TOKEN.getMessage());
	}

	@DisplayName("Refresh 토큰이 저장된 값과 다를 경우 재발급에 실패한다.")
	@Test
	void renew_refresh_token_with_wrong_token() {
		Member member = MemberFixture.create();
		doReturn(member.id().toString()).when(verifyJwtPortIn).extractSubject(anyString());
		doReturn(REFRESH_TOKEN_WRONG).when(authTokenRepository).getRefreshToken(member.id());

		assertThatThrownBy(() -> sut.update(REFRESH_TOKEN))
			.isInstanceOf(AuthenticationFailException.class)
			.hasMessageContaining(AuthErrorCode.INVALID_REFRESH_TOKEN.getMessage());
	}
}
