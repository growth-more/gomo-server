package com.gomo.app.member.domain.model;

import com.gomo.app.common.domain.ValueObject;
import com.gomo.app.common.exception.PolicyViolationException;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.regex.Pattern;

import static com.gomo.app.common.exception.DomainErrorCode.INVALID_PARAMETER;

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
        ensureNotBlank(password);
        ensureValidLength(password);
        ensureValidPasswordRule(password);
        this.password = password;
    }

    public static Password of(String password) {
        return new Password(password);
    }

    public Password update(String password) {
        return Password.of(password);
    }

    private void ensureNotBlank(String password){
        if(password == null || password.isBlank()){
            throw new PolicyViolationException(INVALID_PARAMETER, "password must not be blank");
        }
    }

    private void ensureValidLength(String password){
        if (password.length() > MAX_PASSWORD_LENGTH) {
            throw new PolicyViolationException(INVALID_PARAMETER, "password must not exceed 64 characters");
        }

        if (password.length() < MIN_PASSWORD_LENGTH){
            throw new PolicyViolationException(INVALID_PARAMETER, "password must at least 8 characters");
        }
    }

    private void ensureValidPasswordRule(String password){
        if(!PASSWORD_RULE_PATTERN.matcher(password).matches()){
            throw new PolicyViolationException(INVALID_PARAMETER, "password must not contain forbidden characters");
        }
    }
}
