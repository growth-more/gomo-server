package com.gomo.app.core.member.integration;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.support.auth.domain.repository.AuthCodeRepository;

@DisplayName("[Domain Integration]: 이메일 인증코드 Redis DB 테스트")
public class AuthCodeRepositoryTest extends IntegrationTestBase {

	@Autowired
	AuthCodeRepository sut;

	private static final String EMAIL = "test@gamil.com";
	private static final String CODE = "123456";

	@BeforeEach
	void setUp() {
		sut.save(EMAIL, CODE);
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
