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
public class RegistrantId implements Serializable {

    private UUID id;

    protected RegistrantId() {
    }

    private RegistrantId(UUID id) {
        this.id = id;
    }

    public static RegistrantId of(UUID id) {
        return new RegistrantId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RegistrantId registrantId = (RegistrantId)o;
        return Objects.equals(this.id, registrantId.id);
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
