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
@DisplayName("[Domain unit]: 비밀번호 생성 및 수정 테스트")
public class PasswordTest {

    @Mock
    PasswordService passwordService;

    private static final String PASSWORD = "Test1234!";
    private static final String ENCRYPTED_PASSWORD = "ENCRYPTED_PASSWORD";
    private static final String BLANK = "        ";
    private static final String TOO_SHORT_PASSWORD = "Test1!";
    private static final String TOO_LONG_PASSWORD = Stream.generate(() -> PASSWORD).limit(65).collect(Collectors.joining());
    private static final String FORBIDDEN_PASSWORD = "invalidapssword";

    @DisplayName("비밀번호를 생성한다.")
    @Test
    void create_password() {
        when(passwordService.encode(PASSWORD)).thenReturn(ENCRYPTED_PASSWORD);
        Password password = Password.of(PASSWORD, passwordService);

        assertThat(password.getPassword()).isEqualTo(ENCRYPTED_PASSWORD);
    }

    @DisplayName("null을 입력하면 비밀번호는 생성할 수 없다.")
    @Test
    void create_password_with_null(){
        assertThatThrownBy(() -> Password.of(null, passwordService))
                .isInstanceOf(PasswordConstraintViolationException.class)
                .hasMessageContaining(PasswordErrorCode.BLANK.getMessage());
    }

    @DisplayName("빈칸을 입력하면 비밀번호는 생성할 수 없다.")
    @Test
    void crate_password_with_blank(){
        assertThatThrownBy(() -> Password.of(BLANK, passwordService))
                .isInstanceOf(PasswordConstraintViolationException.class)
                .hasMessageContaining(PasswordErrorCode.BLANK.getMessage());
    }

    @DisplayName("최소 길이보다 짧은 비밀번호는 생성할 수 없다.")
    @Test
    void create_password_with_too_short_password(){
        assertThatThrownBy(() -> Password.of(TOO_SHORT_PASSWORD, passwordService))
                .isInstanceOf(PasswordConstraintViolationException.class)
                .hasMessageContaining(PasswordErrorCode.TOO_SHORT.getMessage());
    }

    @DisplayName("최대 길이보다 긴 비밀번호는 생성할 수 없다.")
    @Test
    void crate_password_with_too_long_password(){
        assertThatThrownBy(() -> Password.of(TOO_LONG_PASSWORD, passwordService))
                .isInstanceOf(PasswordConstraintViolationException.class)
                .hasMessageContaining(PasswordErrorCode.TOO_LONG.getMessage());
    }

    @DisplayName("규칙을 위배한 비밀번호는 생성할 수 없다.")
    @Test
    void create_password_with_forbidden_characters(){
        assertThatThrownBy(() -> Password.of(FORBIDDEN_PASSWORD, passwordService))
                .isInstanceOf(PasswordConstraintViolationException.class)
                .hasMessageContaining(PasswordErrorCode.FORBIDDEN.getMessage());
    }

    @DisplayName("비밀번호가 올바르면 검증에 성공한다.")
    @Test
    void match_password_with_correct_password(){
        when(passwordService.encode(PASSWORD)).thenReturn(ENCRYPTED_PASSWORD);
        when(passwordService.matches(PASSWORD, ENCRYPTED_PASSWORD)).thenReturn(true);
        Password password = Password.of(PASSWORD, passwordService);
        password.matches(PASSWORD, passwordService);
    }

    @DisplayName("비밀번호가 틀리면 검증에 실패한다.")
    @Test
    void match_password_with_incorrect_password(){
        when(passwordService.encode(PASSWORD)).thenReturn(ENCRYPTED_PASSWORD);
        when(passwordService.matches("WrongPassword!", ENCRYPTED_PASSWORD)).thenReturn(false);
        Password password = Password.of(PASSWORD, passwordService);

        assertThatThrownBy(() -> password.matches("WrongPassword!", passwordService))
                .isInstanceOf(MemberAuthenticationFailedException.class)
                .hasMessageContaining(MemberErrorCode.AUTHENTICATION_FAILED.getMessage());
    }

    @DisplayName("비밀번호를 업데이트한다.")
    @Test
    void update_password() {
        when(passwordService.encode(PASSWORD)).thenReturn(ENCRYPTED_PASSWORD);
        when(passwordService.matches(PASSWORD, ENCRYPTED_PASSWORD)).thenReturn(true);
        Password password = Password.of(PASSWORD, passwordService);

        when(passwordService.encode("Updated123!")).thenReturn(ENCRYPTED_PASSWORD);
        Password updatedPassword = password.update(PASSWORD,"Updated123!", passwordService);

        assertThat(updatedPassword.getPassword()).isEqualTo(ENCRYPTED_PASSWORD);
    }

    @DisplayName("null을 입력하면 비밀번호는 수정할 수 없다.")
    @Test
    void update_password_with_null(){
        when(passwordService.encode(PASSWORD)).thenReturn(ENCRYPTED_PASSWORD);
        when(passwordService.matches(PASSWORD, ENCRYPTED_PASSWORD)).thenReturn(true);
        Password password = Password.of(PASSWORD, passwordService);

        assertThatThrownBy(() -> password.update(PASSWORD, null, passwordService))
                .isInstanceOf(PasswordConstraintViolationException.class)
                .hasMessageContaining(PasswordErrorCode.BLANK.getMessage());
    }

    @DisplayName("빈칸을 입력하면 비밀번호는 수정할 수 없다.")
    @Test
    void update_password_with_blank(){
        when(passwordService.encode(PASSWORD)).thenReturn(ENCRYPTED_PASSWORD);
        when(passwordService.matches(PASSWORD, ENCRYPTED_PASSWORD)).thenReturn(true);
        Password password = Password.of(PASSWORD, passwordService);

        assertThatThrownBy(() -> password.update(PASSWORD, BLANK, passwordService))
                .isInstanceOf(PasswordConstraintViolationException.class)
                .hasMessageContaining(PasswordErrorCode.BLANK.getMessage());
    }

    @DisplayName("최소 길이보다 짧은 비밀번호로 수정할 수 없다.")
    @Test
    void update_password_with_too_short_password(){
        when(passwordService.encode(PASSWORD)).thenReturn(ENCRYPTED_PASSWORD);
        when(passwordService.matches(PASSWORD, ENCRYPTED_PASSWORD)).thenReturn(true);
        Password password = Password.of(PASSWORD, passwordService);
        assertThatThrownBy(() -> password.update(PASSWORD, TOO_SHORT_PASSWORD, passwordService))
                .isInstanceOf(PasswordConstraintViolationException.class)
                .hasMessageContaining(PasswordErrorCode.TOO_SHORT.getMessage());
    }

    @DisplayName("최대 길이보다 긴 비밀번호로 수정할 수 없다.")
    @Test
    void update_password_with_too_long_password(){
        when(passwordService.encode(PASSWORD)).thenReturn(ENCRYPTED_PASSWORD);
        when(passwordService.matches(PASSWORD, ENCRYPTED_PASSWORD)).thenReturn(true);
        Password password = Password.of(PASSWORD, passwordService);

        assertThatThrownBy(() -> password.update(PASSWORD,TOO_LONG_PASSWORD, passwordService))
                .isInstanceOf(PasswordConstraintViolationException.class)
                .hasMessageContaining(PasswordErrorCode.TOO_LONG.getMessage());
    }

    @DisplayName("비밀번호 규칙에 위배되는 비밀번호로 수정할 수 없다.")
    @Test
    void update_password_with_forbidden_characters(){
        when(passwordService.encode(PASSWORD)).thenReturn(ENCRYPTED_PASSWORD);
        when(passwordService.matches(PASSWORD, ENCRYPTED_PASSWORD)).thenReturn(true);
        Password password = Password.of(PASSWORD, passwordService);

        assertThatThrownBy(() -> password.update(PASSWORD, FORBIDDEN_PASSWORD, passwordService))
                .isInstanceOf(PasswordConstraintViolationException.class)
                .hasMessageContaining(PasswordErrorCode.FORBIDDEN.getMessage());
    }

    @DisplayName("비밀번호수정 시 기존 비밀번호 검증에 실패하면, 틀리면 검증에 실패한다.")
    @Test
    void update_password_with_incorrect_exist_password(){
        when(passwordService.encode(PASSWORD)).thenReturn(ENCRYPTED_PASSWORD);
        when(passwordService.matches("WrongPassword!", ENCRYPTED_PASSWORD)).thenReturn(false);
        Password password = Password.of(PASSWORD, passwordService);

        assertThatThrownBy(() -> password.update("WrongPassword!", "Updated123!", passwordService))
                .isInstanceOf(MemberAuthenticationFailedException.class)
                .hasMessageContaining(MemberErrorCode.AUTHENTICATION_FAILED.getMessage());
    }
}
