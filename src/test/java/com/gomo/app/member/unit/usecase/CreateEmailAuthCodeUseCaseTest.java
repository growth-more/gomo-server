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
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.repository.EmailAuthCodeRepository;
import com.gomo.app.member.domain.service.AuthCodeGenerator;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.infrastructure.EmailAuthSenderService;
import com.gomo.app.member.presentation.request.CreateEmailAuthCodeRequest;
import com.gomo.app.member.presentation.request.CreatePasswordAuthCodeRequest;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application unit] : 이메일 인증코드 생성 및 전송 테스트")
public class CreateEmailAuthCodeUseCaseTest {

	@InjectMocks
	CreateEmailAuthCodeUseCase sut;

	@Mock
	MemberService memberService;

	@Mock
	EmailAuthCodeRepository emailAuthCodeRepository;

	@Mock
	EmailAuthSenderService emailAuthSenderService;

	@Mock
	AuthCodeGenerator authCodeGenerator;

	private static final String EMAIL = "test@gmail.com";
	private static final String AUTH_CODE = "123456";

	@DisplayName("회원가입 관련 이메일 인증 코드를 생성한다.")
	@Test
	void create_email_auth_code_successfully() {
		doNothing().when(memberService).checkEmailDuplicated(any(Email.class));
		doReturn(AUTH_CODE).when(authCodeGenerator).generate();
		doNothing().when(emailAuthSenderService).sendEmailAuthCode(anyString(), anyString());

		assertThatCode(() -> sut.create(CreateEmailAuthCodeRequest.of(EMAIL))).doesNotThrowAnyException();
	}

	@DisplayName("비밀번호 초기화 관련 이메일 인증 코드를 생성한다.")
	@Test
	void create_email_auth_code_for_password_successfully() {
		doReturn(null).when(memberService).findByEmail(any(Email.class));
		doReturn(AUTH_CODE).when(authCodeGenerator).generate();
		doNothing().when(emailAuthSenderService).sendEmailAuthCode(anyString(), anyString());

		assertThatCode(() -> sut.createPwReset(CreatePasswordAuthCodeRequest.of(EMAIL))).doesNotThrowAnyException();
	}
}
