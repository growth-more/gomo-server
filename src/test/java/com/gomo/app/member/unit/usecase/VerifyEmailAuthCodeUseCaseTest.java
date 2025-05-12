package com.gomo.app.member.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.member.application.VerifyEmailAuthCodeUseCase;
import com.gomo.app.member.domain.service.EmailAuthCodeService;
import com.gomo.app.member.exception.MemberAuthenticationFailedException;
import com.gomo.app.member.exception.code.MemberErrorCode;
import com.gomo.app.member.presentation.request.VerifyEmailAuthCodeRequest;
import com.gomo.app.member.presentation.response.VerifyEmailAuthCodeResponse;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application Unit]: Email 인증 검증 기능 테스트")
public class VerifyEmailAuthCodeUseCaseTest {

	@InjectMocks
	VerifyEmailAuthCodeUseCase sut;

	@Mock
	private EmailAuthCodeService emailAuthCodeService;

	private static final String AUTH_CODE_STORED = "123456";
	private static final String EMAIL = "test@test.com";

	@DisplayName("이메일 인증코드 검증에 성공한다")
	@Test
	void verify_email_auth_code_successfully() {
		VerifyEmailAuthCodeRequest request = VerifyEmailAuthCodeRequest.of(EMAIL, AUTH_CODE_STORED);
		doReturn(AUTH_CODE_STORED).when(emailAuthCodeService).find(anyString());
		doNothing().when(emailAuthCodeService).remove(anyString());

		VerifyEmailAuthCodeResponse expected = VerifyEmailAuthCodeResponse.of(EMAIL);
		VerifyEmailAuthCodeResponse actual = sut.verify(request);

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}

	@DisplayName("이메일 검증코드가 null이면 검증에 실패한다")
	@Test
	void verify_email_auth_code_fail_with_null() {
		VerifyEmailAuthCodeRequest request = VerifyEmailAuthCodeRequest.of(EMAIL, null);
		doReturn(AUTH_CODE_STORED).when(emailAuthCodeService).find(anyString());

		assertThatThrownBy(() -> sut.verify(request))
			.isInstanceOf(MemberAuthenticationFailedException.class)
			.hasMessageContaining(MemberErrorCode.AUTHENTICATION_FAILED.getMessage());
	}

	@DisplayName("이메일 검증코드가 저장된 값과 다르면 검증에 실패한다")
	@Test
	void verify_email_auth_code_fail_with_incorrect_code() {
		VerifyEmailAuthCodeRequest request = VerifyEmailAuthCodeRequest.of(EMAIL, "111111");
		doReturn(AUTH_CODE_STORED).when(emailAuthCodeService).find(anyString());

		assertThatThrownBy(() -> sut.verify(request))
			.isInstanceOf(MemberAuthenticationFailedException.class)
			.hasMessageContaining(MemberErrorCode.AUTHENTICATION_FAILED.getMessage());
	}
}
