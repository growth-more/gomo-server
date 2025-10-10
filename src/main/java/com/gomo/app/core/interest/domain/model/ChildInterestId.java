package com.gomo.app.core.interest.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import com.gomo.app.common.arch.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class ChildInterestId implements Serializable {

    private UUID id;

    protected ChildInterestId() {
    }

    private ChildInterestId(UUID id) {
        this.id = id;
    }

    public static ChildInterestId of(InterestId interestId) {
        return new ChildInterestId(interestId.getId());
    }

    public InterestId toInterestId() {
        return InterestId.of(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChildInterestId childInterestId = (ChildInterestId)o;
        return Objects.equals(id, childInterestId.id);
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
