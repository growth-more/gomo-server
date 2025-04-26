package com.gomo.app.member.domain.model;

import static com.gomo.app.member.exception.code.MemberErrorCode.*;

import java.util.regex.Pattern;

import com.gomo.app.common.ValueObject;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.exception.MemberAuthenticationFailedException;
import com.gomo.app.member.exception.PasswordConstraintViolationException;
import com.gomo.app.member.exception.code.PasswordErrorCode;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class Password {

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 64;
    private static final Pattern PASSWORD_RULE_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])[A-Za-z\\d@#$%^&+=!]+$");

    private String password;

    protected Password() {
    }

    private Password(String password) {
        this.password = password;
    }

    public static Password of(String rawPassword, PasswordService passwordService) {
        ensureNotBlank(rawPassword);
        ensureValidLength(rawPassword);
        ensureValidPasswordRule(rawPassword);
        return new Password(passwordService.encode(rawPassword));
    }

    public void matches(String rawPassword, PasswordService passwordService) {
        if(!passwordService.matches(rawPassword, this.password)) {
            throw new MemberAuthenticationFailedException(AUTHENTICATION_FAILED);
        }
    }

    public Password update(String existPassword, String newPassword, PasswordService passwordService) {
        matches(existPassword, passwordService);
        return Password.of(newPassword, passwordService);
    }

    private static void ensureNotBlank(String password){
        if(password == null || password.isBlank()){
            throw new PasswordConstraintViolationException(PasswordErrorCode.BLANK);
        }
    }

    private static void ensureValidLength(String password){
        if (password.length() < MIN_PASSWORD_LENGTH){
            throw new PasswordConstraintViolationException(PasswordErrorCode.TOO_SHORT);
        }

        if (password.length() > MAX_PASSWORD_LENGTH) {
            throw new PasswordConstraintViolationException(PasswordErrorCode.TOO_LONG);
        }
    }

    private static void ensureValidPasswordRule(String password){
        if(!PASSWORD_RULE_PATTERN.matcher(password).matches()){
            throw new PasswordConstraintViolationException(PasswordErrorCode.FORBIDDEN);
        }
    }
}
