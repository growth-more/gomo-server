package com.gomo.app.core.member.domain.model;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.exception.PasswordConstraintViolationException;
import com.gomo.app.core.member.exception.code.PasswordErrorCode;

@DisplayName("[Domain Unit]: 비밀번호 생성 및 수정 테스트")
@ExtendWith(MockitoExtension.class)
public class PasswordTest {

	private static final String PASSWORD = "Test1234!";
	private static final String PASSWORD_BLANK = "        ";
	private static final String TOO_SHORT_PASSWORD = "Test1!";
	private static final String TOO_LONG_PASSWORD = Stream.generate(() -> PASSWORD).limit(65).collect(Collectors.joining());
	private static final String NO_LOWERCASE_PASSWORD = "TEST1234!";
	private static final String NO_UPPERCASE_PASSWORD = "test1234!";
	private static final String NO_DIGIT_PASSWORD = "TESTtest!";
	private static final String NO_SPECIAL_CHAR_PASSWORD = "Test12345";
	private static final String FORBIDDEN_PASSWORD = "Invalidpassword1!*";

	@DisplayName("평문 비밀번호를 생성한다.")
	@Test
	void create_password() {
		Password password = Password.ofRaw(PASSWORD);
		assertThat(password.getPassword()).isEqualTo(PASSWORD);
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

	@DisplayName("소문자가 포함되지 않는 비밀번호는 생성할 수 없다.")
	@Test
	void create_password_with_no_lowercase() {
		assertThatThrownBy(() -> Password.ofRaw(NO_LOWERCASE_PASSWORD))
			.isInstanceOf(PasswordConstraintViolationException.class)
			.hasMessageContaining(PasswordErrorCode.NO_LOWERCASE.getMessage());
	}

	@DisplayName("대문자가 포함되지 않는 비밀번호는 생성할 수 없다.")
	@Test
	void create_password_with_no_uppercase() {
		assertThatThrownBy(() -> Password.ofRaw(NO_UPPERCASE_PASSWORD))
			.isInstanceOf(PasswordConstraintViolationException.class)
			.hasMessageContaining(PasswordErrorCode.NO_UPPERCASE.getMessage());
	}

	@DisplayName("숫자가 포함되지 않는 비밀번호는 생성할 수 없다.")
	@Test
	void create_password_with_no_digit() {
		assertThatThrownBy(() -> Password.ofRaw(NO_DIGIT_PASSWORD))
			.isInstanceOf(PasswordConstraintViolationException.class)
			.hasMessageContaining(PasswordErrorCode.NO_DIGIT.getMessage());
	}

	@DisplayName("특수문자가 포함되지 않는 비밀번호는 생성할 수 없다.")
	@Test
	void create_password_with_no_special_char() {
		assertThatThrownBy(() -> Password.ofRaw(NO_SPECIAL_CHAR_PASSWORD))
			.isInstanceOf(PasswordConstraintViolationException.class)
			.hasMessageContaining(PasswordErrorCode.NO_SPECIAL_CHAR.getMessage());
	}

	@DisplayName("규칙에 위배되는 문자(*)가 들어간 비밀번호는 생성할 수 없다.")
	@Test
	void create_password_with_invalid_character() {
		assertThatThrownBy(() -> Password.ofRaw(FORBIDDEN_PASSWORD))
			.isInstanceOf(PasswordConstraintViolationException.class)
			.hasMessageContaining(PasswordErrorCode.FORBIDDEN.getMessage());
	}
}
