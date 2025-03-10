package com.gomo.app.member.integration;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.member.infrastructure.EmailAuthRedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[Domain integration]: 세션관리 Redis DB 테스트")
public class EmailAuthRedisServiceTest extends IntegrationTestBase {

	@Autowired
	EmailAuthRedisService sut;

	private static final String EMAIL = "test@gmail.com";
    private static final String CODE = "111111";

	@BeforeEach
	void setup(){
		sut.setAuthCode(EMAIL, CODE);
	}

	@DisplayName("Redis에 있는 인증코드를 조회할 수 있다.")
	@Test
	void read_refresh_token(){
		assertThat(sut.getAuthCode(EMAIL)).isEqualTo(CODE);
	}

    @DisplayName("사용된 인증 코드는 삭제된다.")
    @Test
    void delete_auth_code(){
        sut.deleteAuthCode(EMAIL);
        assertThat(sut.getAuthCode(EMAIL)).isNull();
    }

}
