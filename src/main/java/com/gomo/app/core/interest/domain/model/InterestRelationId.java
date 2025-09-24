package com.gomo.app.core.interest.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import com.gomo.app.common.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class InterestRelationId implements Serializable {

    private UUID id;

    protected InterestRelationId() {
    }

    private InterestRelationId(UUID id) {
        this.id = id;
    }

    public static InterestRelationId of(UUID id) {
        return new InterestRelationId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InterestRelationId interestRelationId = (InterestRelationId)o;
        return Objects.equals(id, interestRelationId.id);
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
