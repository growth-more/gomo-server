package com.gomo.app.core.member.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.application.port.out.EmailCodeVerifier;
import com.gomo.app.core.member.application.port.out.EmailTokenCreator;
import com.gomo.app.support.auth.domain.exception.AuthErrorCode;
import com.gomo.app.support.auth.domain.exception.InvalidAuthCodeException;

@DisplayName("[Application Unit]: Email 인증 검증 기능 테스트")
@ExtendWith(MockitoExtension.class)
public class EmailTokenServiceTest {

	@InjectMocks
	private EmailTokenService sut;

	@Mock
	private EmailCodeVerifier emailCodeVerifier;

	@Mock
	private EmailTokenCreator emailTokenCreator;

	private static final String AUTH_CODE_STORED = "123456";
	private static final String EMAIL = "test@test.com";

	@DisplayName("이메일 인증코드 검증에 성공한다")
	@Test
	void issue_email_auth_code_successfully() {
		sut.issue(EMAIL, AUTH_CODE_STORED);
		verify(emailCodeVerifier, times(1)).verify(EMAIL, AUTH_CODE_STORED);
		verify(emailTokenCreator, times(1)).create(EMAIL, 1800);
	}

	@DisplayName("이메일 검증코드가 null이면 검증에 실패한다")
	@Test
	void issue_email_auth_code_fail_with_null() {
		doThrow(new InvalidAuthCodeException(AuthErrorCode.INVALID_AUTH_CODE)).when(emailCodeVerifier).verify(anyString(), any());
		assertThatThrownBy(() -> sut.issue(EMAIL, null))
			.isInstanceOf(InvalidAuthCodeException.class)
			.hasMessageContaining(AuthErrorCode.INVALID_AUTH_CODE.getMessage());
	}

	@DisplayName("이메일 검증코드가 저장된 값과 다르면 검증에 실패한다")
	@Test
	void issue_email_auth_code_fail_with_incorrect_code() {
		doThrow(new InvalidAuthCodeException(AuthErrorCode.INVALID_AUTH_CODE)).when(emailCodeVerifier).verify(anyString(), anyString());
		assertThatThrownBy(() -> sut.issue(EMAIL, "111111"))
			.isInstanceOf(InvalidAuthCodeException.class)
			.hasMessageContaining(AuthErrorCode.INVALID_AUTH_CODE.getMessage());
	}
}
