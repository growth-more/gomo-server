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
public class AchieverId implements Serializable {

    private UUID id;

    protected AchieverId() {
    }

    private AchieverId(UUID id) {
        this.id = id;
    }

    public static AchieverId of(UUID id) {
        return new AchieverId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AchieverId achieverId = (AchieverId)o;
        return Objects.equals(this.id, achieverId.id);
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
