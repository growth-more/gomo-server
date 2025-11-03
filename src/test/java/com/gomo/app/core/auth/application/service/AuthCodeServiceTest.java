package com.gomo.app.core.auth.application.service;

import static com.gomo.app.core.auth.domain.exception.AuthErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.auth.application.port.out.AuthCodeSender;
import com.gomo.app.core.auth.application.port.out.PrincipalEmailChecker;
import com.gomo.app.core.auth.domain.exception.AuthCodeCreateFailException;
import com.gomo.app.core.auth.domain.exception.InvalidAuthCodeException;
import com.gomo.app.core.auth.domain.repository.AuthCodeRepository;

@DisplayName("[Application Unit]: 인증 코드 생성 테스트")
@ExtendWith(MockitoExtension.class)
class AuthCodeServiceTest {

	@InjectMocks
	private AuthCodeService sut;

	@Mock
	private PrincipalEmailChecker principalEmailChecker;

	@Mock
	private AuthCodeSender authCodeSender;

	@Mock
	private AuthCodeRepository authCodeRepository;

	@DisplayName("인증 코드 검증 후, 삭제한다.")
	@Test
	void verify_auth_code() {
		String authCode = "123456";
		doReturn(Optional.of(authCode)).when(authCodeRepository).findByEmail(any());

		sut.verify("email@gmail.com", authCode);

		verify(authCodeRepository, times(1)).delete(any());
	}

	@DisplayName("일치하지 않는 인증 코드로 검증한다.")
	@Test
	void verify_auth_code_with_invalid_code() {
		String authCode = "123456";
		String invalidAuthCode = "654321";
		doReturn(Optional.of(authCode)).when(authCodeRepository).findByEmail(any());

		assertThatThrownBy(() -> sut.verify("email@gmail.com", invalidAuthCode)).isExactlyInstanceOf(InvalidAuthCodeException.class);
	}

	@DisplayName("회원가입을 위해 이메일 인증 코드를 생성한다.")
	@Test
	void create_email_auth_code_for_sign_up() {
		String email = "email@gmail.com";
		doReturn(false).when(principalEmailChecker).exists(email);

		sut.issueForSignUp(email);

		verify(authCodeRepository, times(1)).save(any(), any());
		verify(authCodeSender, times(1)).send(any(), any());
	}

	@DisplayName("이미 회원가입이 완료된 이메일로 회원가입 이메일 인증 코드를 생성한다.")
	@Test
	void create_email_auth_code_for_sign_up_with_existent_email() {
		String email = "email@gmail.com";
		doReturn(true).when(principalEmailChecker).exists(email);

		assertThatThrownBy(() -> sut.issueForSignUp(email))
			.isExactlyInstanceOf(AuthCodeCreateFailException.class)
			.hasMessageContaining(PRINCIPAL_DUPLICATED.getMessage());
	}

	@DisplayName("비밀번호 초기화를 위해 이메일 인증 코드를 생성한다.")
	@Test
	void create_email_auth_code_for_password_reset() {
		String email = "email@gmail.com";
		doReturn(true).when(principalEmailChecker).exists(email);

		sut.issueForPasswordReset(email);

		verify(authCodeRepository, times(1)).save(any(), any());
		verify(authCodeSender, times(1)).send(any(), any());
	}

	@DisplayName("이미 회원가입이 완료된 이메일로 비밀번호 초기화 이메일 인증 코드를 생성한다.")
	@Test
	void create_email_auth_code_password_reset_with_existent_email() {
		String email = "email@gmail.com";
		doReturn(false).when(principalEmailChecker).exists(email);

		assertThatThrownBy(() -> sut.issueForPasswordReset(email))
			.isExactlyInstanceOf(AuthCodeCreateFailException.class)
			.hasMessageContaining(PRINCIPAL_NOT_FOUND.getMessage());
	}
}
