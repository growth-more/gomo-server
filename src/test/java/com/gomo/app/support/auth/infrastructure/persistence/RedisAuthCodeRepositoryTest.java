package com.gomo.app.support.auth.infrastructure.persistence;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.support.auth.domain.repository.AuthCodeRepository;
import com.gomo.app.test.IntegrationTest;

@DisplayName("[Domain Integration]: 이메일 인증코드 Redis DB 테스트")
@IntegrationTest
public class RedisAuthCodeRepositoryTest {

	private static final String EMAIL = "test@gamil.com";

	@Autowired
	private AuthCodeRepository sut;

	@BeforeEach
	void setUp() {
		sut.save(EMAIL, "123456");
	}

	@DisplayName("Redis에 저장된 인증코드를 조회 할 수 있다.")
	@Test
	void read_email_auth_code() {
		assertThat(sut.findByEmail(EMAIL).isPresent()).isTrue();
	}

	@DisplayName("Redis에 저장된 인증코드를 삭제 할 수 있다.")
	@Test
	void delete_email_auth_code() {
		sut.delete(EMAIL);
		assertThat(sut.findByEmail(EMAIL).isPresent()).isFalse();
	}
}
