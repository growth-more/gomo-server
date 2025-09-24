package com.gomo.app.core.member.domain.model;

import java.util.regex.Pattern;

import com.gomo.app.common.ValueObject;
import com.gomo.app.core.member.exception.MemberNameConstraintViolationException;
import com.gomo.app.core.member.exception.code.MemberNameErrorCode;

import jakarta.persistence.Embeddable;
import lombok.Getter;

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
        return MemberName.of(name);
    }

    private void ensureNotBlank(String name){
        if(name == null || name.isBlank()){
            throw new MemberNameConstraintViolationException(MemberNameErrorCode.BLANK);
        }
    }

    private void ensureNameLength(String name){
        if(name.length() < MIN_NAME_LENGTH){
            throw new MemberNameConstraintViolationException(MemberNameErrorCode.TOO_SHORT);
        }

        if(name.length() > MAX_NAME_LENGTH){
            throw new MemberNameConstraintViolationException(MemberNameErrorCode.TOO_LONG);
        }
    }

    private void ensureNameRule(String name){
        if(!NAME_RULE.matcher(name).matches()){
            throw new MemberNameConstraintViolationException(MemberNameErrorCode.FORBIDDEN);
        }
    }

    @Override
    public String toString() {
        return this.name;
    }
}
