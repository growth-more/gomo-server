package com.gomo.app.survey.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import com.gomo.app.common.domain.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class RespondentId implements Serializable {

    private UUID id;

    protected RespondentId() {
    }

    private RespondentId(UUID id) {
        this.id = id;
    }

    public static RespondentId of(UUID uuid) {
        return new RespondentId(uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RespondentId respondentId = (RespondentId)o;
        return Objects.equals(id, respondentId.id);
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
