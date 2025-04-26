package com.gomo.app.member.domain.model;

import java.util.regex.Pattern;

import com.gomo.app.common.ValueObject;
import com.gomo.app.member.exception.HandleConstraintViolationException;
import com.gomo.app.member.exception.code.HandleErrorCode;

import jakarta.persistence.Embeddable;
import lombok.Getter;

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
            throw new HandleConstraintViolationException(HandleErrorCode.DUPLICATED);
        }
        return Handle.of(handle);
    }

    private void ensureNotBlank(String handle){
        if (handle == null || handle.isBlank()) {
            throw new HandleConstraintViolationException(HandleErrorCode.BLANK);
        }
    }

    private void ensureValidHandleLength(String handle) {
        if(handle.length() < MIN_HANDLE_LENGTH){
            throw new HandleConstraintViolationException(HandleErrorCode.TOO_SHORT);
        }

        if(handle.length() > MAX_HANDLE_LENGTH){
            throw new HandleConstraintViolationException(HandleErrorCode.TOO_LONG);
        }
    }

    private void ensureValidHandleRule(String handle){
        if(!HANDLE_PATTERN.matcher(handle).matches()){
            throw new HandleConstraintViolationException(HandleErrorCode.FORBIDDEN);
        }
    }

    @Override
    public String toString() {
        return this.handle;
    }
}
