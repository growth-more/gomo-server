package com.gomo.app.support.auth.infrastructure.persistence;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.support.auth.domain.repository.AuthTokenRepository;

@DisplayName("[Domain Integration]: Auth 토큰 Redis DB 테스트")
public class RedisAuthTokenRepositoryTest extends IntegrationTestBase {

	@Autowired
	AuthTokenRepository sut;

	private static final UUID uuid = UUID.randomUUID();
	private static final String REFRESH_TOKEN = "refresh_token";

	@BeforeEach
	void setUp() {
		sut.setRefreshToken(uuid, REFRESH_TOKEN);
	}

	@DisplayName("Redis에 저장된 refresh token을 조회 할 수 있다.")
	@Test
	void read_refresh_token() {
		assertThat(sut.getRefreshToken(uuid)).isEqualTo("refresh_token");
	}

	@DisplayName("Redis에 저장된 인증코드를 삭제 할 수 있다.")
	@Test
	void delete_email_auth_code() {
		sut.deleteRefreshToken(uuid);
		assertThat(sut.getRefreshToken(uuid)).isNullOrEmpty();
	}
}
