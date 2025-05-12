package com.gomo.app.member.unit.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.member.domain.model.Password;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.exception.MemberAuthenticationFailedException;
import com.gomo.app.member.exception.PasswordConstraintViolationException;
import com.gomo.app.member.exception.code.MemberErrorCode;
import com.gomo.app.member.exception.code.PasswordErrorCode;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Domain Unit]: 비밀번호 생성 및 수정 테스트")
public class PasswordTest {

	@Mock
	PasswordService passwordService;

	private static final String PASSWORD = "Test1234!";
	private static final String PASSWORD_ENCODED = "ENCODED_PASSWORD";
	private static final String PASSWORD_BLANK = "        ";
	private static final String TOO_SHORT_PASSWORD = "Test1!";
	private static final String TOO_LONG_PASSWORD = Stream.generate(() -> PASSWORD)
		.limit(65)
		.collect(Collectors.joining());
	private static final String FORBIDDEN_PASSWORD = "invalidpassword*";

	@DisplayName("평문 비밀번호를 생성한다.")
	@Test
	void create_password() {
		Password password = Password.ofRaw(PASSWORD);
		assertThat(password.getPassword()).isEqualTo(PASSWORD);
	}

	@DisplayName("암호화된 비밀번호를 생성한다.")
	@Test
	void create_encrypted_password() {
		when(passwordService.encode(anyString())).thenReturn(PASSWORD_ENCODED);

		Password rawPassword = Password.ofRaw(PASSWORD);
		Password encodedPassword = rawPassword.encodedWith(passwordService);

		assertThat(encodedPassword.getPassword()).isEqualTo(PASSWORD_ENCODED);
	}

	@DisplayName("null을 입력하면 비밀번호는 생성할 수 없다.")
	@Test
	void create_password_with_null() {
		assertThatThrownBy(() -> Password.ofRaw(null))
			.isInstanceOf(PasswordConstraintViolationException.class)
			.hasMessageContaining(PasswordErrorCode.BLANK.getMessage());
	}

	@DisplayName("빈칸을 입력하면 비밀번호는 생성할 수 없다.")
	@Test
	void create_password_with_blank() {
		assertThatThrownBy(() -> Password.ofRaw(PASSWORD_BLANK))
			.isInstanceOf(PasswordConstraintViolationException.class)
			.hasMessageContaining(PasswordErrorCode.BLANK.getMessage());
	}

	@DisplayName("최소 길이보다 짧은 비밀번호는 생성할 수 없다.")
	@Test
	void create_password_with_too_short_password() {
		assertThatThrownBy(() -> Password.ofRaw(TOO_SHORT_PASSWORD))
			.isInstanceOf(PasswordConstraintViolationException.class)
			.hasMessageContaining(PasswordErrorCode.TOO_SHORT.getMessage());
	}

	@DisplayName("최대 길이보다 긴 비밀번호는 생성할 수 없다.")
	@Test
	void create_password_with_too_long_password() {
		assertThatThrownBy(() -> Password.ofRaw(TOO_LONG_PASSWORD))
			.isInstanceOf(PasswordConstraintViolationException.class)
			.hasMessageContaining(PasswordErrorCode.TOO_LONG.getMessage());
	}

	@DisplayName("규칙에 위배되는 문자(*)가 들어간 비밀번호는 생성할 수 없다.")
	@Test
	void create_password_with_invalid_character() {
		assertThatThrownBy(() -> Password.ofRaw(FORBIDDEN_PASSWORD))
			.isInstanceOf(PasswordConstraintViolationException.class)
			.hasMessageContaining(PasswordErrorCode.FORBIDDEN.getMessage());
	}

	@DisplayName("비밀번호가 맞으면 검증에 성공한다.")
	@Test
	void verify_password_with_correct_password() {
		when(passwordService.matches(anyString(), anyString())).thenReturn(true);

		Password password = Password.ofRaw(PASSWORD);
		password.verifyWith(passwordService, password);
	}

	@DisplayName("비밀번호가 틀리면 검증에 실패한다.")
	@Test
	void verify_password_with_incorrect_password() {
		when(passwordService.matches(anyString(), anyString())).thenReturn(false);

		Password password = Password.ofRaw(PASSWORD);
		assertThatThrownBy(() -> password.verifyWith(passwordService, password))
			.isInstanceOf(MemberAuthenticationFailedException.class)
			.hasMessageContaining(MemberErrorCode.AUTHENTICATION_FAILED.getMessage());
	}
}
