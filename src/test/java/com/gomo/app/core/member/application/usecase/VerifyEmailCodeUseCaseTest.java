package com.gomo.app.core.member.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.application.port.dto.VerifyEmailAuthCodeDto;
import com.gomo.app.core.member.presentation.request.VerifyEmailCodeRequest;
import com.gomo.app.support.auth.domain.repository.AuthCodeRepository;
import com.gomo.app.support.auth.exception.AuthErrorCode;
import com.gomo.app.support.auth.exception.InvalidAuthCodeException;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application Unit]: Email 인증 검증 기능 테스트")
public class VerifyEmailCodeUseCaseTest {

	@InjectMocks
	VerifyEmailCodeUseCase sut;

	@Mock
	private AuthCodeRepository authCodeRepository;

	private static final String AUTH_CODE_STORED = "123456";
	private static final String EMAIL = "test@test.com";

	@DisplayName("이메일 인증코드 검증에 성공한다")
	@Test
	void verify_email_auth_code_successfully() {
		VerifyEmailCodeRequest request = VerifyEmailCodeRequest.of(EMAIL, AUTH_CODE_STORED);
		doReturn(Optional.of(AUTH_CODE_STORED)).when(authCodeRepository).findByEmail(anyString());
		doNothing().when(authCodeRepository).delete(anyString());

		VerifyEmailAuthCodeDto expected = VerifyEmailAuthCodeDto.of(EMAIL);
		VerifyEmailAuthCodeDto actual = sut.verify(request.getEmail(), request.getCode());

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}

	@DisplayName("이메일 검증코드가 null이면 검증에 실패한다")
	@Test
	void verify_email_auth_code_fail_with_null() {
		VerifyEmailCodeRequest request = VerifyEmailCodeRequest.of(EMAIL, null);
		doReturn(Optional.of(AUTH_CODE_STORED)).when(authCodeRepository).findByEmail(anyString());
		assertThatThrownBy(() -> sut.verify(request.getEmail(), request.getCode()))
			.isInstanceOf(InvalidAuthCodeException.class)
			.hasMessageContaining(AuthErrorCode.INVALID_AUTH_CODE.getMessage());
	}

	@DisplayName("이메일 검증코드가 저장된 값과 다르면 검증에 실패한다")
	@Test
	void verify_email_auth_code_fail_with_incorrect_code() {
		VerifyEmailCodeRequest request = VerifyEmailCodeRequest.of(EMAIL, "111111");
		doReturn(Optional.of(AUTH_CODE_STORED)).when(authCodeRepository).findByEmail(anyString());
		assertThatThrownBy(() -> sut.verify(request.getEmail(), request.getCode()))
			.isInstanceOf(InvalidAuthCodeException.class)
			.hasMessageContaining(AuthErrorCode.INVALID_AUTH_CODE.getMessage());
	}
}
