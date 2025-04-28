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
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.infrastructure.EmailAuthRedisService;
import com.gomo.app.member.infrastructure.EmailAuthSenderService;
import com.gomo.app.member.presentation.request.CreateEmailAuthCodeRequest;
import com.gomo.app.member.presentation.response.CreateEmailAuthCodeResponse;

@DisplayName("[Application Unit]: 이메일 인증코드 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class CreateEmailAuthCodeUseCaseTest {
	@InjectMocks
	CreateEmailAuthCodeUseCase sut;

	@Mock
	MemberService memberService;

	@Mock
	EmailAuthRedisService emailAuthRedisService;

	@Mock
	EmailAuthSenderService emailAuthSenderService;

	private static final String EMAIL = "test@google.com";

	@DisplayName("이메일 인증코드를 생성한다.")
	@Test
	void create_email_auth_code_successfully() {
		doNothing().when(memberService).checkEmailDuplicated(any());
		doNothing().when(emailAuthSenderService).sendEmailAuthCode(anyString(), anyString());

		CreateEmailAuthCodeResponse expected = CreateEmailAuthCodeResponse.of(EMAIL);
		CreateEmailAuthCodeResponse actual = sut.create(CreateEmailAuthCodeRequest.of(EMAIL));

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}
}
