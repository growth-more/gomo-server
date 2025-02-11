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
public class Email {

    private static final int MAX_EMAIL_LENGTH = 254; // RFC 5322
    private static final int MIN_EMAIL_LENGTH = 10;
    private static final Pattern EMAIL_RULES = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private String email;

    protected Email() {
    }

    private Email(String email) {
        ensureNotBlank(email);
        ensureValidEmailLength(email);
        ensureValidEmailRule(email);
        this.email = email;
    }

    public static Email of(String email) {
        return new Email(email);
    }

    private void ensureNotBlank(String email){
        if (email == null || email.isBlank()) {
            throw new PolicyViolationException(INVALID_PARAMETER,"Email should not be blank");
        }
    }

    private void ensureValidEmailLength(String email){
        if (email.length() < MIN_EMAIL_LENGTH){
            throw new PolicyViolationException(INVALID_PARAMETER,"Email should be at least 10 characters");
        }

        if (email.length() > MAX_EMAIL_LENGTH){
            throw new PolicyViolationException(INVALID_PARAMETER,"Email should be at most 254 characters");
        }
    }

    private void ensureValidEmailRule(String email){
        if (!EMAIL_RULES.matcher(email).matches()){
            throw new PolicyViolationException(INVALID_PARAMETER,"Email should be in valid format");
        }
    }

    @Override
    public String toString() {
        return this.email;
    }
}
