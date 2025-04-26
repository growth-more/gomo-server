package com.gomo.app.point.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import com.gomo.app.common.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class PointWalletId implements Serializable {

    private UUID id;

    protected PointWalletId() {
    }

    private PointWalletId(UUID id) {
        this.id = id;
    }

    public static PointWalletId of(UUID uuid) {
        return new PointWalletId(uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PointWalletId pointWalletId = (PointWalletId)o;
        return Objects.equals(id, pointWalletId.id);
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
