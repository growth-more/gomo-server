package com.gomo.app.member.domain.model;

import com.gomo.app.common.domain.ValueObject;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class Password {

    private String password;

    protected Password() {
    }

    private Password(String password) {
        this.password = password;
    }

    public static Password of(String password) {
        return new Password(password);
    }

    public Password update(String password) {
        return Password.of(password);
    }

    private boolean isValidLength(int min, int max) {
        return false;
    }

    private boolean doesContainProhibitCharacters() {
        return false;
    }
}
