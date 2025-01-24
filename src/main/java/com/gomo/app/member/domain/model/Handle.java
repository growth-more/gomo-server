package com.gomo.app.member.domain.model;

import com.gomo.app.common.domain.ValueObject;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class Handle {

    private String handle;

    protected Handle() {
    }

    private Handle(String handle) {
        this.handle = handle;
    }

    public static Handle of(String handle) {
        return new Handle(handle);
    }

    public Handle update(String handle) {
        return Handle.of(handle);
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
        return this.handle;
    }
}
