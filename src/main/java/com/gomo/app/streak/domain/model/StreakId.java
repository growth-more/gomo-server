package com.gomo.app.streak.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import com.gomo.app.common.domain.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class StreakId implements Serializable {

    private UUID id;

    protected StreakId() {
    }

    private StreakId(UUID id) {
        this.id = id;
    }

    public static StreakId of(UUID id) {
        return new StreakId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        StreakId streakId = (StreakId)o;
        return Objects.equals(id, streakId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
