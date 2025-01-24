package com.gomo.app.member.domain.model;

import com.gomo.app.common.domain.ValueObject;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class Email {

    private String email;

    protected Email() {
    }

    private Email(String email) {
        this.email = email;
    }

    public static Email of(String email) {
        return new Email(email);
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
        return this.email;
    }
}
