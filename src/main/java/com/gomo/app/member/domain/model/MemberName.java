package com.gomo.app.member.domain.model;

import com.gomo.app.common.domain.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class MemberName {

    private String name;

    protected MemberName() {
    }

    private MemberName(String name) {
        this.name = name;
    }

    public static MemberName of(String name) {
        return new MemberName(name);
    }

    public MemberName update(String name) {
        return MemberName.of(name);
    }

    private boolean isValidLength(
        int min,
        int max
    ) {
        return false;
    }

    private boolean doesContainProhibitCharacters() {
        return false;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
