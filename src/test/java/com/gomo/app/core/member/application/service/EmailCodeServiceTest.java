package com.gomo.app.core.member.application.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.application.port.out.EmailCodeSender;

@DisplayName("[Application unit] : 이메일 인증코드 생성 및 전송 테스트")
@ExtendWith(MockitoExtension.class)
public class EmailCodeServiceTest {

	@InjectMocks
	private EmailCodeService sut;

	@Mock
	private EmailService emailService;

	@Mock
	private MemberService memberService;

	@Mock
	private EmailCodeSender emailCodeSender;

	private static final String EMAIL = "test@gmail.com";

	@DisplayName("회원가입 관련 이메일 인증 코드를 생성한다.")
	@Test
	void create_email_auth_code_successfully() {
		sut.issueForSignUp(EMAIL);

		verify(emailService, times(1)).validateDuplicated(any());
		verify(emailCodeSender, times(1)).send(any());
	}

	@DisplayName("비밀번호 초기화 관련 이메일 인증 코드를 생성한다.")
	@Test
	void create_email_auth_code_for_password_successfully() {
		sut.issueForPasswordReset(EMAIL);

		verify(memberService, times(1)).findByEmail(any());
		verify(emailCodeSender, times(1)).send(any());
	}
}
