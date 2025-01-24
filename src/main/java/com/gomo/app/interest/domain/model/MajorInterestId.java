package com.gomo.app.interest.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import com.gomo.app.common.domain.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class MajorInterestId implements Serializable {

    private UUID id;

    protected MajorInterestId() {
    }

    private MajorInterestId(UUID id) {
        this.id = id;
    }

    public static MajorInterestId of(UUID id) {
        return new MajorInterestId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MajorInterestId majorInterestId = (MajorInterestId)o;
        return Objects.equals(id, majorInterestId.id);
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
