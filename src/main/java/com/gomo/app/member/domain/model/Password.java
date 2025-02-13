package com.gomo.app.member.domain.model;

import com.gomo.app.common.domain.ValueObject;
import com.gomo.app.common.exception.PolicyViolationException;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.exception.MemberAuthenticationFailedException;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.regex.Pattern;

import static com.gomo.app.common.exception.DomainErrorCode.INVALID_PARAMETER;
import static com.gomo.app.member.exception.MemberErrorCode.AUTHENTICATION_FAILED;

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

    public static Password fromRaw(String rawPassword) {
        ensureNotBlank(rawPassword);
        ensureValidLength(rawPassword);
        ensureValidPasswordRule(rawPassword);
        return new Password(rawPassword);
    }

    public static Password fromEncoded(String encodedPassword) {
        return new Password(encodedPassword);
    }

    public void matches(String rawPassword, boolean isMatched){
        if(!isMatched){
            throw new MemberAuthenticationFailedException(AUTHENTICATION_FAILED, "password incorrect");
        }
    }

    private static void ensureNotBlank(String password){
        if(password == null || password.isBlank()){
            throw new PolicyViolationException(INVALID_PARAMETER, "password must not be blank");
        }
    }

    private static void ensureValidLength(String password){
        if (password.length() > MAX_PASSWORD_LENGTH) {
            throw new PolicyViolationException(INVALID_PARAMETER, "password must not exceed 64 characters");
        }

        if (password.length() < MIN_PASSWORD_LENGTH){
            throw new PolicyViolationException(INVALID_PARAMETER, "password must at least 8 characters");
        }
    }

    private static void ensureValidPasswordRule(String password){
        if(!PASSWORD_RULE_PATTERN.matcher(password).matches()){
            throw new PolicyViolationException(INVALID_PARAMETER, "password must not contain forbidden characters");
        }
    }
}
