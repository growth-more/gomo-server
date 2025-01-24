package com.gomo.app.member.domain.model;

import com.gomo.app.common.domain.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class Motto {

    private String motto;

    protected Motto() {
    }

    private Motto(String motto) {
        this.motto = motto;
    }

    public static Motto of(String motto) {
        return new Motto(motto);
    }

    public Motto update(String motto) {
        return Motto.of(motto);
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
        return this.motto;
    }
}
