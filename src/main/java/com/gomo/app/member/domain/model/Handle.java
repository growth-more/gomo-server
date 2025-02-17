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
public class Handle {

    private static final int MIN_HANDLE_LENGTH = 3;
    private static final int MAX_HANDLE_LENGTH = 30;
    private static final Pattern HANDLE_PATTERN = Pattern.compile("^@[a-zA-Z0-9_.]+$");

    private String handle;

    protected Handle() {
    }

    private Handle(String handle) {
        ensureNotBlank(handle);
        ensureValidHandleLength(handle);
        ensureValidHandleRule(handle);
        this.handle = handle;
    }

    public static Handle of(String handle) {
        return new Handle(handle);
    }

    public Handle update(String handle) {
        if(this.handle.equals(handle)){
            throw new PolicyViolationException(INVALID_PARAMETER, "Handle is already same");
        }

        return Handle.of(handle);
    }

    private void ensureNotBlank(String handle){
        if (handle == null || handle.isBlank()) {
            throw new PolicyViolationException(INVALID_PARAMETER,"Handle must not be blank");
        }
    }

    private void ensureValidHandleLength(String handle) {
        if(handle.length() < MIN_HANDLE_LENGTH){
            throw new PolicyViolationException(INVALID_PARAMETER, "Handle must be at least 3 characters");
        }

        if(handle.length() > MAX_HANDLE_LENGTH){
            throw new PolicyViolationException(INVALID_PARAMETER, "Handle must not exceed 30 characters");
        }
    }

    private void ensureValidHandleRule(String handle){
        if(!HANDLE_PATTERN.matcher(handle).matches()){
            throw new PolicyViolationException(INVALID_PARAMETER, "Handle must contain only alphabets, numbers, and special characters: . _ -");
        }
    }

    @Override
    public String toString() {
        return this.handle;
    }
}
