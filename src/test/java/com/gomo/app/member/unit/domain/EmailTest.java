package com.gomo.app.member.unit.domain;

import com.gomo.app.common.exception.PolicyViolationException;
import com.gomo.app.member.domain.model.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("[Domain unit]: 이메일 생성 테스트")
public class EmailTest {

    private static final String EMAIL = "test@test.com";
    private static final String BLANK = "   ";
    private static final String TOO_SHORT_EMAIL = "a@a.a";
    private static final String TOO_LONG_EMAIL = "a@a.a" + "a".repeat(250) + ".com";
    private static final String INVALID_EMAIL = "@missingusername.com";

    @DisplayName("이메일을 생성한다.")
    @Test
    void create_email(){
        Email email = Email.of(EMAIL);
        assertThat(email.toString()).isEqualTo(EMAIL);
    }

    @DisplayName("null을 입력하면 이메일은 생성할 수 없다.")
    @Test
    void create_email_with_null(){
        assertThatThrownBy(() -> Email.of(null))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("Email should not be blank");
    }

    @DisplayName("공백만 있는 이메일은 생성할 수 없다.")
    @Test
    void create_email_with_blank(){
        assertThatThrownBy(() -> Email.of(BLANK))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("Email should not be blank");
    }

    @DisplayName("최소 길이보다 짧은 이메일은 생성할 수 없다.")
    @Test
    void create_email_with_short_length(){
        assertThatThrownBy(() -> Email.of(TOO_SHORT_EMAIL))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("Email should be at least 10 characters");
    }

    @DisplayName("최대 길이보다 긴 이메일은 생성할 수 없다.")
    @Test
    void create_email_with_long_length(){
        assertThatThrownBy(() -> Email.of(TOO_LONG_EMAIL))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("Email should be at most 254 characters");
    }

    @DisplayName("유효하지 않은 이메일 형식은 생성할 수 없다.")
    @Test
    void create_email_with_invalid_email(){
        assertThatThrownBy(() -> Email.of(INVALID_EMAIL))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("Email should be invalid format");
    }
}