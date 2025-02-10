package com.gomo.app.member.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import com.gomo.app.common.domain.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class MemberId implements Serializable {

    private UUID id;

    protected MemberId() {
    }

    private MemberId(UUID id) {
        this.id = id;
    }

    public static MemberId of(UUID id) {
        return new MemberId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemberId memberId = (MemberId)o;
        return Objects.equals(this.id, memberId.id);
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
