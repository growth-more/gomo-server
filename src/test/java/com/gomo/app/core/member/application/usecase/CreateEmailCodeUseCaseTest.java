package com.gomo.app.core.member.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.core.member.fixture.MemberFixture;
import com.gomo.app.support.auth.application.port.CreateAuthCodePortIn;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application unit] : 이메일 인증코드 생성 및 전송 테스트")
public class CreateEmailCodeUseCaseTest {

	@InjectMocks
	CreateEmailCodeUseCase sut;

	@Mock
	MemberService memberService;

	@Mock
	CreateAuthCodePortIn createAuthCodePortIn;

	private static final String EMAIL = "test@gmail.com";

	@DisplayName("회원가입 관련 이메일 인증 코드를 생성한다.")
	@Test
	void create_email_auth_code_successfully() {
		doNothing().when(memberService).checkEmailDuplicated(any(Email.class));
		doNothing().when(createAuthCodePortIn).sendToEmail(anyString());
		assertThatCode(() -> sut.createForSignUp(EMAIL)).doesNotThrowAnyException();
	}

	@DisplayName("비밀번호 초기화 관련 이메일 인증 코드를 생성한다.")
	@Test
	void create_email_auth_code_for_password_successfully() {
		doReturn(MemberFixture.member()).when(memberService).findByEmail(any(Email.class));
		doNothing().when(createAuthCodePortIn).sendToEmail(anyString());
		assertThatCode(() -> sut.createForPasswordReset(EMAIL)).doesNotThrowAnyException();
	}
}
