package com.gomo.app.member.domain.model;

import com.gomo.app.common.domain.ValueObject;

import com.gomo.app.common.exception.DomainErrorCode;
import com.gomo.app.common.exception.PolicyViolationException;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.regex.Pattern;

import static com.gomo.app.common.exception.DomainErrorCode.INVALID_PARAMETER;

@Getter
@Embeddable
@ValueObject
public class MemberName {

    private static final int MAX_NAME_LENGTH = 20;
    private static final int MIN_NAME_LENGTH = 2;
    private static final Pattern NAME_RULE = Pattern.compile("^(?!.*\\\\.\\\\.)(?!.*--)(?!.*__)[a-zA-Z0-9ㄱ-힣._-]+$");

    private String name;

    protected MemberName() {
    }

    private MemberName(String name) {
        ensureNotBlank(name);
        ensureNameLength(name);
        ensureNameRule(name);
        this.name = name;
    }

    public static MemberName of(String name) {
        return new MemberName(name);
    }

    public MemberName update(String name) {
        if (this.name.equals(name)){
            throw new PolicyViolationException(INVALID_PARAMETER, "name must not be same with origin name");
        }
        return MemberName.of(name);
    }

    private void ensureNotBlank(String name){
        if(name == null || name.isBlank()){
            throw new PolicyViolationException(INVALID_PARAMETER, "name must not be blank");
        }
    }

    private void ensureNameLength(String name){
        if(name.length() < MIN_NAME_LENGTH){
            throw new PolicyViolationException(INVALID_PARAMETER, "name must not be less than 2 characters");
        }

        if(name.length() > MAX_NAME_LENGTH){
            throw new PolicyViolationException(INVALID_PARAMETER, "name must not be more than 20 characters");
        }
    }

    private void ensureNameRule(String name){
        if(!NAME_RULE.matcher(name).matches()){
            throw new PolicyViolationException(INVALID_PARAMETER, "name must contain only letters and numbers");
        }
    }

    @Override
    public String toString() {
        return this.name;
    }
}
