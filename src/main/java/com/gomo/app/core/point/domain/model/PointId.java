package com.gomo.app.core.point.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import com.gomo.app.common.arch.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class PointId implements Serializable {

    private UUID id;

    protected PointId() {
    }

    private PointId(UUID id) {
        this.id = id;
    }

    public static PointId of(UUID uuid) {
        return new PointId(uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PointId pointId = (PointId)o;
        return Objects.equals(id, pointId.id);
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
