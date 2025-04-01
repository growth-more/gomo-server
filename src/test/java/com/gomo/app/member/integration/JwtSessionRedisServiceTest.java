package com.gomo.app.member.integration;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.member.domain.model.*;
import com.gomo.app.member.infrastructure.JwtSessionRedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[Domain integration]: 세션관리 Redis DB 테스트")
public class JwtSessionRedisServiceTest extends IntegrationTestBase {

	@Autowired
	JwtSessionRedisService sut;

	private static final String REFRESH_TOKEN = "refreshToken";
	private static final MemberId MEMBER_ID = MemberId.of(UUID.randomUUID());

	@BeforeEach
	void setup(){
		sut.setRefreshToken(MEMBER_ID, REFRESH_TOKEN);
	}

	@DisplayName("Redis에 있는 토큰을 조회할 수 있다.")
	@Test
	void read_refresh_token(){
		assertThat(sut.getRefreshToken(MEMBER_ID)).isEqualTo(REFRESH_TOKEN);
	}

	@DisplayName("Redis에 있는 토큰을 삭제할 수 있다.")
	@Test
	void delete_refresh_token(){
		sut.deleteRefreshToken(MEMBER_ID);
		assertThat(sut.getRefreshToken(MEMBER_ID)).isNull();
	}
}
