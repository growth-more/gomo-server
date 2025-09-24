package com.gomo.app.auth.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.support.auth.application.AuthTokenGenerator;
import com.gomo.app.support.auth.domain.model.AuthToken;
import com.gomo.app.support.auth.domain.repository.AuthTokenRepository;
import com.gomo.app.common.util.JwtUtil;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application Unit]: 인증 토큰 발급 테스트")
public class AuthTokenGeneratorTest {

	@InjectMocks
	AuthTokenGenerator sut;

	@Mock
	private JwtUtil jwtUtil;

	@Mock
	private AuthTokenRepository authTokenRepository;

	@DisplayName("인증토큰 발급에 성공한다.")
	@Test
	void generate_auth_token_success() {

		AuthToken expected = AuthToken.of("access", "refresh");

		doReturn("access").when(jwtUtil).generateAccessToken(any());
		doReturn("refresh").when(jwtUtil).generateRefreshToken(any());
		doNothing().when(authTokenRepository).setRefreshToken(any(), anyString());

		AuthToken actual = sut.generate(UUID.randomUUID());

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}
}
