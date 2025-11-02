package com.gomo.app.support.auth.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.fixture.MemberFixture;
import com.gomo.app.support.auth.application.port.dto.AuthTokenDto;
import com.gomo.app.support.auth.application.port.out.JwtCreator;
import com.gomo.app.support.auth.application.port.out.JwtVerifier;
import com.gomo.app.support.auth.domain.exception.AuthErrorCode;
import com.gomo.app.support.auth.domain.exception.AuthenticationFailException;
import com.gomo.app.support.auth.domain.model.AuthToken;
import com.gomo.app.support.auth.domain.repository.AuthTokenRepository;

@DisplayName("[Application Unit]: 인증 토큰 발급 테스트")
@ExtendWith(MockitoExtension.class)
public class AuthTokenServiceTest {

	@InjectMocks
	private AuthTokenService sut;

	@Mock
	private JwtCreator jwtCreator;

	@Mock
	private JwtVerifier jwtVerifier;

	@Mock
	private AuthTokenRepository authTokenRepository;

	@Nested
	@DisplayName("리프레시 토큰 재발급 테스트")
	class CreateAuthToken {

		@DisplayName("인증토큰 발급에 성공한다.")
		@Test
		void create_auth_token() {
			AuthToken expected = AuthToken.of("access", "refresh");

			doReturn("access").when(jwtCreator).createAccessToken(any());
			doReturn("refresh").when(jwtCreator).createRefreshToken(any());
			doNothing().when(authTokenRepository).saveRefreshToken(any(), anyString());

			AuthToken actual = sut.create(UUID.randomUUID());

			assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
		}
	}

	@Nested
	@DisplayName("리프레시 토큰 재발급 테스트")
	class UpdateRefreshToken {

		private static final String REFRESH_TOKEN = "REFRESH_TOKEN";
		private static final String REFRESH_TOKEN_WRONG = "WRONG_TOKEN";

		@DisplayName("리프레시 토큰을 재발급한다.")
		@Test
		void renew_refresh_token() {
			Member member = MemberFixture.create();
			AuthToken authToken = AuthToken.of("access", "refresh");
			AuthTokenDto expected = AuthTokenDto.of(member.getId(), authToken.getAccessToken(), authToken.getRefreshToken(), 1L);

			doReturn(member.getId().toString()).when(jwtVerifier).extractSubject(anyString());
			doReturn(REFRESH_TOKEN).when(authTokenRepository).findRefreshToken(member.getId());
			doReturn("access").when(jwtCreator).createAccessToken(any());
			doReturn("refresh").when(jwtCreator).createRefreshToken(any());
			doReturn(1L).when(jwtVerifier).extractExpirationTime(anyString());

			AuthTokenDto actual = sut.update(REFRESH_TOKEN);

			assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
		}

		@DisplayName("리프레시 토큰이 null로 들어올 경우 재발급에 실패한다.")
		@Test
		void renew_refresh_token_with_null() {
			assertThatThrownBy(() -> sut.update(null))
				.isInstanceOf(AuthenticationFailException.class)
				.hasMessageContaining(AuthErrorCode.MISSING_REFRESH_TOKEN.getMessage());
		}

		@DisplayName("리프레시 토큰이 저장된 값과 다를 경우 재발급에 실패한다.")
		@Test
		void renew_refresh_token_with_wrong_token() {
			Member member = MemberFixture.create();
			doReturn(member.getId().toString()).when(jwtVerifier).extractSubject(anyString());
			doReturn(REFRESH_TOKEN_WRONG).when(authTokenRepository).findRefreshToken(member.getId());

			assertThatThrownBy(() -> sut.update(REFRESH_TOKEN))
				.isInstanceOf(AuthenticationFailException.class)
				.hasMessageContaining(AuthErrorCode.INVALID_REFRESH_TOKEN.getMessage());
		}
	}

	@Nested
	@DisplayName("리프레시 토큰 삭제 테스트")
	class DeleteAuthToken {

		@DisplayName("리프레시 토큰을 삭제한다.")
		@Test
		void delete_refresh_token() {
			sut.delete(UUID.randomUUID());

			verify(authTokenRepository, times(1)).deleteRefreshToken(any());
		}
	}
}
