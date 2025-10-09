package com.gomo.app.support.auth.infrastructure.persistence;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.support.auth.domain.repository.AuthTokenRepository;
import com.gomo.app.test.IntegrationTest;

@DisplayName("[Domain Integration]: Auth 토큰 Redis DB 테스트")
@IntegrationTest
public class RedisAuthTokenRepositoryTest {

	private static final UUID TOKEN_ID = UUID.randomUUID();

	@Autowired
	private AuthTokenRepository sut;

	@BeforeEach
	void setUp() {
		sut.setRefreshToken(TOKEN_ID, "refresh_token");
	}

	@DisplayName("Redis에 저장된 refresh token을 조회 할 수 있다.")
	@Test
	void read_refresh_token() {
		assertThat(sut.getRefreshToken(TOKEN_ID)).isEqualTo("refresh_token");
	}

	@DisplayName("Redis에 저장된 인증코드를 삭제 할 수 있다.")
	@Test
	void delete_email_auth_code() {
		sut.deleteRefreshToken(TOKEN_ID);
		assertThat(sut.getRefreshToken(TOKEN_ID)).isNullOrEmpty();
	}
}
