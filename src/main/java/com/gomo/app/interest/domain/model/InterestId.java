package com.gomo.app.interest.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import com.gomo.app.common.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class InterestId implements Serializable {

    private UUID id;

    protected InterestId() {
    }

    private InterestId(UUID id) {
        this.id = id;
    }

    public static InterestId of(UUID id) {
        return new InterestId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InterestId interestId = (InterestId)o;
        return Objects.equals(id, interestId.id);
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
