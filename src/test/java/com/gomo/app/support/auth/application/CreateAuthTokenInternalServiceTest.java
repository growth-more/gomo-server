package com.gomo.app.support.auth.application;

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

import com.gomo.app.common.jwt.port.GenerateJwtPortIn;
import com.gomo.app.support.auth.domain.model.AuthToken;
import com.gomo.app.support.auth.domain.repository.AuthTokenRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application Unit]: 인증 토큰 발급 테스트")
public class CreateAuthTokenInternalServiceTest {

	@InjectMocks
	CreateAuthTokenInternalService sut;

	@Mock
	private GenerateJwtPortIn generateJwtPortIn;

	@Mock
	private AuthTokenRepository authTokenRepository;

	@DisplayName("인증토큰 발급에 성공한다.")
	@Test
	void create_auth_token_success() {
		AuthToken expected = AuthToken.of("access", "refresh");

		doReturn("access").when(generateJwtPortIn).generateAccessToken(any());
		doReturn("refresh").when(generateJwtPortIn).generateRefreshToken(any());
		doNothing().when(authTokenRepository).setRefreshToken(any(), anyString());

		AuthToken actual = sut.create(UUID.randomUUID());

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}
}
