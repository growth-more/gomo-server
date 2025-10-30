package com.gomo.app.core.member.adapter.out.client;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.support.auth.application.port.JwtCreator;
import com.gomo.app.support.auth.application.port.JwtVerifier;

@DisplayName("[Adapter Unit]: 임시토큰 요청 테스트")
@ExtendWith(MockitoExtension.class)
class TemporaryTokenClientTest {

	@InjectMocks
	private EmailTokenClient sut;

	@Mock
	private JwtVerifier jwtVerifier;

	@Mock
	private JwtCreator jwtCreator;

	@DisplayName("임시토큰을 생성한다.")
	@Test
	void create_temporary_token() {
		String token = "temporaryToken";
		doReturn(token).when(jwtCreator).createTemporaryToken(any(), anyLong());

		String actual = sut.create("email", 1800);

		assertThat(actual).isEqualTo(token);
	}

	@DisplayName("임시토큰을 검증한다.")
	@Test
	void verify_temporary_token() {
		doReturn(true).when(jwtVerifier).verify(any());

		assertThatCode(() -> sut.verify("temporaryToken")).doesNotThrowAnyException();
		verify(jwtVerifier, times(1)).verify(any());
	}

	@DisplayName("유효하지 않은 임시토큰으로 검증한다.")
	@Test
	void verify_invalid_temporary_token() {
		doReturn(false).when(jwtVerifier).verify(any());

		assertThatThrownBy(() -> sut.verify("temporaryToken")).isExactlyInstanceOf(IllegalArgumentException.class);
		verify(jwtVerifier, times(1)).verify(any());
	}
}
