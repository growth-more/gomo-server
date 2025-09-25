package com.gomo.app.core.member.domain.model;

import java.util.regex.Pattern;

import com.gomo.app.common.arch.ValueObject;
import com.gomo.app.core.member.exception.MottoConstraintViolationException;
import com.gomo.app.core.member.exception.code.MottoErrorCode;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class Motto {

    private static final int MAX_MOTTO_LENGTH = 200;
    private static final Pattern VALID_MOTTO_PATTERN = Pattern.compile("^[a-zA-Z0-9ㄱ-힣!@#$%^&*()_+=,.?\\s]+$");

    private String motto;

    protected Motto() {
    }

    private Motto(String motto) {
        ensureValidMottoLength(motto);
        ensureValidMottoRules(motto);
        this.motto = motto;
    }

    public static Motto of(String motto) {
        return new Motto(motto);
    }

    public Motto update(String motto) {
       return Motto.of(motto);
    }

    private void ensureValidMottoLength(String motto){
        if(motto.length() > MAX_MOTTO_LENGTH){
            throw new MottoConstraintViolationException(MottoErrorCode.TOO_LONG);
        }
    }

    private void ensureValidMottoRules(String motto){
        if(!VALID_MOTTO_PATTERN.matcher(motto).matches()){
            throw new MottoConstraintViolationException(MottoErrorCode.FORBIDDEN);
        }
    }

    @Override
    public String toString() {
        return this.motto;
    }
}
