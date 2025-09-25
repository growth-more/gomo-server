package com.gomo.app.core.member.unit.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.exception.EmailConstraintViolationException;
import com.gomo.app.core.member.exception.code.EmailErrorCode;

@DisplayName("[Domain unit]: 이메일 생성 테스트")
public class EmailTest {

	private static final String EMAIL = "test@test.com";
	private static final String BLANK = "   ";
	private static final String TOO_SHORT_EMAIL = "a@a.a";
	private static final String TOO_LONG_EMAIL = "a@a.a" + "a".repeat(250) + ".com";
	private static final String INVALID_EMAIL = "@missingusername.com";

	@DisplayName("이메일을 생성한다.")
	@Test
	void create_email() {
		Email email = Email.of(EMAIL);
		assertThat(email.toString()).isEqualTo(EMAIL);
	}

	@DisplayName("null을 입력하면 이메일은 생성할 수 없다.")
	@Test
	void create_email_with_null() {
		assertThatThrownBy(() -> Email.of(null))
			.isInstanceOf(EmailConstraintViolationException.class)
			.hasMessageContaining(EmailErrorCode.BLANK.getMessage());
	}

	@DisplayName("공백만 있는 이메일은 생성할 수 없다.")
	@Test
	void create_email_with_blank() {
		assertThatThrownBy(() -> Email.of(BLANK))
			.isInstanceOf(EmailConstraintViolationException.class)
			.hasMessageContaining(EmailErrorCode.BLANK.getMessage());
	}

	@DisplayName("최소 길이보다 짧은 이메일은 생성할 수 없다.")
	@Test
	void create_email_with_short_length() {
		assertThatThrownBy(() -> Email.of(TOO_SHORT_EMAIL))
			.isInstanceOf(EmailConstraintViolationException.class)
			.hasMessageContaining(EmailErrorCode.TOO_SHORT.getMessage());
	}

	@DisplayName("최대 길이보다 긴 이메일은 생성할 수 없다.")
	@Test
	void create_email_with_long_length() {
		assertThatThrownBy(() -> Email.of(TOO_LONG_EMAIL))
			.isInstanceOf(EmailConstraintViolationException.class)
			.hasMessageContaining(EmailErrorCode.TOO_LONG.getMessage());
	}

	@DisplayName("유효하지 않은 이메일 형식은 생성할 수 없다.")
	@Test
	void create_email_with_invalid_email() {
		assertThatThrownBy(() -> Email.of(INVALID_EMAIL))
			.isInstanceOf(EmailConstraintViolationException.class)
			.hasMessageContaining(EmailErrorCode.INVALID_FORMAT.getMessage());
	}
}
