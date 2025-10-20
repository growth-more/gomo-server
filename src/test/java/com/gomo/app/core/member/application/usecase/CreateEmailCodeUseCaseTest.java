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

@DisplayName("[Application unit] : 이메일 인증코드 생성 및 전송 테스트")
@ExtendWith(MockitoExtension.class)
public class CreateEmailCodeUseCaseTest {

	@InjectMocks
	private CreateEmailCodeUseCase sut;

	@Mock
	private MemberService memberService;

	@Mock
	private CreateAuthCodePortIn createAuthCodePortIn;

	private static final String EMAIL = "test@gmail.com";

	@DisplayName("회원가입 관련 이메일 인증 코드를 생성한다.")
	@Test
	void create_email_auth_code_successfully() {
		String authCode = "000000";
		doNothing().when(memberService).checkEmailDuplicated(any(Email.class));
		doReturn(authCode).when(createAuthCodePortIn).sendToEmail(anyString());

		String actual = sut.createForSignUp(EMAIL);

		assertThat(actual).isEqualTo(authCode);
	}

	@DisplayName("비밀번호 초기화 관련 이메일 인증 코드를 생성한다.")
	@Test
	void create_email_auth_code_for_password_successfully() {
		String authCode = "000000";
		doReturn(MemberFixture.create()).when(memberService).findByEmail(any(Email.class));
		doReturn(authCode).when(createAuthCodePortIn).sendToEmail(anyString());

		String actual = sut.createForPasswordReset(EMAIL);

		assertThat(actual).isEqualTo(authCode);
	}
}
