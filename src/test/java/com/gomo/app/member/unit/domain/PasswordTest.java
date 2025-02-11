package com.gomo.app.member.unit.domain;

import com.gomo.app.common.exception.PolicyViolationException;
import com.gomo.app.member.domain.model.Password;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@DisplayName("[Domain unit]: 비밀번호 생성 및 수정 테스트")
public class PasswordTest {
    private static final String PASSWORD = "Test1234!";
    private static final String BLANK = "        ";
    private static final String TOO_SHORT_PASSWORD = "Test1!";
    private static final String TOO_LONG_PASSWORD = Stream.generate(() -> PASSWORD).limit(64).collect(Collectors.joining());
    private static final String FORBIDDEN_PASSWORD = "invalidapssword";

    @DisplayName("비밀번호를 생성한다.")
    @Test
    void create_password() {
        Password password = Password.of(PASSWORD);

        assertThat(password.getPassword()).isEqualTo(PASSWORD);
    }

    @DisplayName("null을 입력하면 비밀번호는 생성할 수 없다.")
    @Test
    void crate_password_with_null(){
        assertThatThrownBy(() -> Password.of(null))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("password must not be blank");
    }

    @DisplayName("빈칸을 입력하면 비밀번호는 생성할 수 없다.")
    @Test
    void crate_password_with_blank(){
        assertThatThrownBy(() -> Password.of(BLANK))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("password must not be blank");
    }

    @DisplayName("최소 길이보다 짧은 비밀번호는 생성할 수 없다.")
    @Test
    void create_password_with_too_short_password(){
        assertThatThrownBy(() -> Password.of(TOO_SHORT_PASSWORD))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("password must at least 8 characters");
    }

    @DisplayName("최대 길이보다 긴 비밀번호는 생성할 수 없다.")
    @Test
    void crate_password_with_too_long_password(){
        assertThatThrownBy(() -> Password.of(TOO_LONG_PASSWORD))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("password must not exceed 64 characters");
    }

    @DisplayName("규칙을 위배한 비밀번호는 생성할 수 없다.")
    @Test
    void create_password_with_forbidden_characters(){
        assertThatThrownBy(() -> Password.of(FORBIDDEN_PASSWORD))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("password must not contain forbidden characters");
    }

    @DisplayName("비밀번호를 업데이트한다.")
    @Test
    void update_password() {
        Password password = Password.of(PASSWORD);
        Password updatedPassword = password.update("Updated123!");

        assertThat(updatedPassword.getPassword()).isEqualTo("Updated123!");
    }

    @DisplayName("null을 입력하면 비밀번호는 수정할 수 없다.")
    @Test
    void update_password_with_null(){
        Password password = Password.of(PASSWORD);

        assertThatThrownBy(() -> password.update(null))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("password must not be blank");
    }

    @DisplayName("빈칸을 입력하면 비밀번호는 수정할 수 없다.")
    @Test
    void update_password_with_blank(){
        Password password = Password.of(PASSWORD);

        assertThatThrownBy(() -> password.update(BLANK))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("password must not be blank");
    }

    @DisplayName("최소 길이보다 짧은 비밀번호로 수정할 수 없다.")
    @Test
    void update_password_with_too_short_password(){
        Password password = Password.of(PASSWORD);
        assertThatThrownBy(() -> password.update(TOO_SHORT_PASSWORD))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("password must at least 8 characters");
    }

    @DisplayName("최대 길이보다 긴 비밀번호로 수정할 수 없다.")
    @Test
    void update_password_with_too_long_password(){
        Password password = Password.of(PASSWORD);

        assertThatThrownBy(() -> password.update(TOO_LONG_PASSWORD))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("password must not exceed 64 characters");
    }

    @DisplayName("비밀번호 규칙에 위배되는 비밀번호로 수정할 수 없다.")
    @Test
    void update_password_with_forbidden_characters(){
        Password password = Password.of(PASSWORD);

        assertThatThrownBy(() -> password.update(FORBIDDEN_PASSWORD))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("password must not contain forbidden characters");
    }
}
