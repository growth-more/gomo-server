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
        if(this.motto.equals(motto)){
            throw new PolicyViolationException(INVALID_PARAMETER, "Motto cannot update with same motto");
        }

        return Motto.of(motto);
    }

    private void ensureValidMottoLength(String motto){
        if(motto.length() > MAX_MOTTO_LENGTH){
            throw new PolicyViolationException(INVALID_PARAMETER, "Motto must not exceed 200 characters");
        }
    }

    private void ensureValidMottoRules(String motto){
        if(!VALID_MOTTO_PATTERN.matcher(motto).matches()){
            throw new PolicyViolationException(INVALID_PARAMETER, "Motto must comply with the motto rules");
        }
    }

    @Override
    public String toString() {
        return this.motto;
    }
}
