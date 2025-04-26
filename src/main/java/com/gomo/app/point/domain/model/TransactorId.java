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
public class TransactorId implements Serializable {

    private UUID id;

    protected TransactorId() {
    }

    private TransactorId(UUID id) {
        this.id = id;
    }

    public static TransactorId of(UUID uuid) {
        return new TransactorId(uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransactorId transactorId = (TransactorId)o;
        return Objects.equals(id, transactorId.id);
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
