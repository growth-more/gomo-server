package com.gomo.app.core.member.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.common.jwt.port.GenerateJwtPortIn;
import com.gomo.app.support.auth.application.port.VerifyAuthCodePortIn;
import com.gomo.app.support.auth.exception.AuthErrorCode;
import com.gomo.app.support.auth.exception.InvalidAuthCodeException;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application Unit]: Email 인증 검증 기능 테스트")
public class VerifyEmailCodeUseCaseTest {

	@InjectMocks
	VerifyEmailCodeUseCase sut;

	@Mock
	private VerifyAuthCodePortIn verifyAuthCodePortIn;

	@Mock
	private GenerateJwtPortIn generateJwtPortIn;

	private static final String AUTH_CODE_STORED = "123456";
	private static final String EMAIL = "test@test.com";

	@DisplayName("이메일 인증코드 검증에 성공한다")
	@Test
	void verify_email_auth_code_successfully() {
		sut.verify(EMAIL, AUTH_CODE_STORED);
		verify(verifyAuthCodePortIn, times(1)).verify(EMAIL, AUTH_CODE_STORED);
		verify(generateJwtPortIn, times(1)).generateTemporaryToken(EMAIL, 300);
	}

	@DisplayName("이메일 검증코드가 null이면 검증에 실패한다")
	@Test
	void verify_email_auth_code_fail_with_null() {
		doThrow(new InvalidAuthCodeException(AuthErrorCode.INVALID_AUTH_CODE)).when(verifyAuthCodePortIn).verify(anyString(), any());
		assertThatThrownBy(() -> sut.verify(EMAIL, null))
			.isInstanceOf(InvalidAuthCodeException.class)
			.hasMessageContaining(AuthErrorCode.INVALID_AUTH_CODE.getMessage());
	}

	@DisplayName("이메일 검증코드가 저장된 값과 다르면 검증에 실패한다")
	@Test
	void verify_email_auth_code_fail_with_incorrect_code() {
		doThrow(new InvalidAuthCodeException(AuthErrorCode.INVALID_AUTH_CODE)).when(verifyAuthCodePortIn).verify(anyString(), anyString());
		assertThatThrownBy(() -> sut.verify(EMAIL, "111111"))
			.isInstanceOf(InvalidAuthCodeException.class)
			.hasMessageContaining(AuthErrorCode.INVALID_AUTH_CODE.getMessage());
	}
}
