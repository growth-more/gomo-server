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
public class ParentInterestId implements Serializable {

    private UUID id;

    protected ParentInterestId() {
    }

    private ParentInterestId(UUID id) {
        this.id = id;
    }

    public static ParentInterestId of(InterestId interestId) {
        return new ParentInterestId(interestId.getId());
    }

    public InterestId toInterestId() {
        return InterestId.of(this.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParentInterestId parentInterestId = (ParentInterestId)o;
        return Objects.equals(id, parentInterestId.id);
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
