package com.gomo.app.member.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.member.application.CreateEmailAuthCodeUseCase;
import com.gomo.app.member.domain.service.MemberValidator;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.exception.EmailDuplicatedException;
import com.gomo.app.member.exception.code.EmailErrorCode;
import com.gomo.app.member.infrastructure.EmailAuthRedisService;
import com.gomo.app.member.infrastructure.EmailAuthSenderService;
import com.gomo.app.member.presentation.request.CreateEmailAuthCodeRequest;
import com.gomo.app.member.presentation.response.CreateEmailAuthCodeResponse;

@DisplayName("[Application Unit]: 이메일 인증코드 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class CreateEmailAuthUseCaseTest {
	@InjectMocks
	CreateEmailAuthCodeUseCase sut;

	@Mock
	EmailAuthRedisService emailAuthRedisService;

	@Mock
	MemberValidator memberValidator;

	@Mock
	EmailAuthSenderService emailAuthSenderService;

	@Mock
	PasswordService passwordService;

	private static final String EMAIL = "test@google.com";

	@DisplayName("이메일 인증코드 생성에 성공한다.")
	@Test
	void create_email_auth_code_successfully() {

		doNothing().when(memberValidator).checkDuplicatedEmail(anyString());
		doNothing().when(emailAuthSenderService).sendEmailAuthCode(anyString(), anyString());

		CreateEmailAuthCodeResponse expected = CreateEmailAuthCodeResponse.of(EMAIL);
		CreateEmailAuthCodeResponse actual = sut.create(CreateEmailAuthCodeRequest.of(EMAIL));

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}

	@DisplayName("이미 사용중인 이메일일 경우 인증코드 생성에 실패한다.")
	@Test
	void create_email_auth_code_with_duplicated() {
		doThrow(new EmailDuplicatedException(EmailErrorCode.DUPLICATED)).when(memberValidator).checkDuplicatedEmail(EMAIL);

		assertThatThrownBy(() -> sut.create(CreateEmailAuthCodeRequest.of(EMAIL)))
			.isInstanceOf(EmailDuplicatedException.class)
			.hasMessageContaining(EmailErrorCode.DUPLICATED.getMessage());
	}
}
