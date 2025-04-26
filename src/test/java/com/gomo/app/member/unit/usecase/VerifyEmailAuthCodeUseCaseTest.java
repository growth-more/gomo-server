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
import com.gomo.app.member.exception.MemberAuthenticationFailedException;
import com.gomo.app.member.exception.code.MemberErrorCode;
import com.gomo.app.member.infrastructure.EmailAuthRedisService;
import com.gomo.app.member.presentation.request.VerifyEmailAuthCodeRequest;
import com.gomo.app.member.presentation.response.VerifyEmailAuthCodeResponse;

@DisplayName("[Application Unit]: AccessToken 재발급 테스트")
@ExtendWith(MockitoExtension.class)
public class VerifyEmailAuthCodeUseCaseTest {
	@InjectMocks
	VerifyEmailAuthCodeUseCase sut;

	@Mock
	EmailAuthRedisService emailAuthRedisService;

	private static final String AUTH_CODE = "111111";
	private static final String EMAIL = "test@gmail.com";

	@DisplayName("이메일 인증코드 검증에 성공한다.")
	@Test
	void verify_email_auth_successfully() {
		VerifyEmailAuthCodeRequest request = VerifyEmailAuthCodeRequest.of(EMAIL, AUTH_CODE);
		doReturn(AUTH_CODE).when(emailAuthRedisService).getAuthCode(anyString());
		doNothing().when(emailAuthRedisService).deleteAuthCode(anyString());

		VerifyEmailAuthCodeResponse expected = VerifyEmailAuthCodeResponse.of(EMAIL);
		VerifyEmailAuthCodeResponse actual = sut.verify(request);

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}

	@DisplayName("검증코드가 null이면 이메일 인증코드 검증에 실패한다.")
	@Test
	void verify_email_auth_with_null_auth_code() {
		VerifyEmailAuthCodeRequest request = VerifyEmailAuthCodeRequest.of(EMAIL, null);

		assertThatThrownBy(() -> sut.verify(request))
			.isInstanceOf(MemberAuthenticationFailedException.class)
			.hasMessageContaining(MemberErrorCode.AUTHENTICATION_FAILED.getMessage());
	}

	@DisplayName("저장된 검증코드와 다르면, 이메일 인증코드 검증에 실패한다.")
	@Test
	void verify_email_auth_with_wrong_auth_code() {
		VerifyEmailAuthCodeRequest request = VerifyEmailAuthCodeRequest.of(EMAIL, "222222");
		doReturn(AUTH_CODE).when(emailAuthRedisService).getAuthCode(anyString());

		assertThatThrownBy(() -> sut.verify(request))
			.isInstanceOf(MemberAuthenticationFailedException.class)
			.hasMessageContaining(MemberErrorCode.AUTHENTICATION_FAILED.getMessage());
	}
}
