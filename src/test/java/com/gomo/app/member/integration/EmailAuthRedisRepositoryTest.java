package com.gomo.app.member.integration;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.member.infrastructure.repository.EmailAuthRedisRepository;

@DisplayName("[Domain integration]: 이메일 인증 관리 Redis DB 테스트")
public class EmailAuthRedisRepositoryTest extends IntegrationTestBase {

	@Autowired
	EmailAuthRedisRepository sut;

	private static final String EMAIL = "test@gmail.com";
	private static final String CODE = "111111";

	@BeforeEach
	void setup() {
		sut.setAuthCode(EMAIL, CODE);
	}

	@DisplayName("Redis에 있는 인증코드를 조회할 수 있다.")
	@Test
	void read_refresh_token() {
		assertThat(sut.getAuthCode(EMAIL)).isEqualTo(CODE);
	}

	@DisplayName("검증이 완료된 인증 코드는 삭제된다.")
	@Test
	void delete_auth_code() {
		sut.deleteAuthCode(EMAIL);
		assertThat(sut.getAuthCode(EMAIL)).isNull();
	}

}
