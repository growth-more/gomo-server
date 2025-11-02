package com.gomo.app.support.auth.adapter.out.lib;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[Adapter unit]: jwt 테스트")
class JwtClientTest {

	private JwtClient jwtClient;
	private final String testSecret = "this-is-a-sufficiently-long-secret-key-for-testing-purposes";
	private final long accessExp = 60 * 60;
	private final long refreshExp = 24 * 60 * 60;

	@BeforeEach
	void setUp() {
		jwtClient = new JwtClient(testSecret, accessExp, refreshExp);
	}

	@DisplayName("토큰을 생성한 후, 유효성을 검증한다.")
	@Test
	void generate_and_validate_token() {
		UUID subject = UUID.randomUUID();

		String accessToken = jwtClient.createAccessToken(subject);

		assertThat(accessToken).isNotBlank();
		assertThat(jwtClient.verify(accessToken)).isTrue();
		assertThat(subject.toString()).isEqualTo(jwtClient.extractSubject(accessToken));
	}

	@DisplayName("이미 만료된 토큰의 유효성을 검증한다.")
	@Test
	void validate_with_already_expired_token() throws InterruptedException {
		String expiredToken = jwtClient.createTemporaryToken("test", 1);
		Thread.sleep(1100);

		boolean actual = jwtClient.verify(expiredToken);

		assertThat(actual).isFalse();
	}

	@DisplayName("잘못된 서명을 가진 토큰의 유효성을 검증한다.")
	@Test
	void validate_with_invalid_signature() {
		UUID subject = UUID.randomUUID();
		String token = jwtClient.createAccessToken(subject);
		String malformedToken = token.substring(0, token.length() - 1) + "XXX";

		boolean actual = jwtClient.verify(malformedToken);

		assertThat(actual).isFalse();
	}

	@DisplayName("null로 유효성을 검증한다.")
	@Test
	void validate_with_null_token() {
		boolean actual = jwtClient.verify(null);

		assertThat(actual).isFalse();
	}
}
