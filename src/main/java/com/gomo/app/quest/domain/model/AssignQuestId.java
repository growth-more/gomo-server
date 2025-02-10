package com.gomo.app.quest.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import com.gomo.app.common.domain.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class AssignQuestId implements Serializable {

    private UUID id;

    protected AssignQuestId() {
    }

    private AssignQuestId(UUID id) {
        this.id = id;
    }

    public static AssignQuestId of(UUID id) {
        return new AssignQuestId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AssignQuestId assignQuestId = (AssignQuestId)o;
        return Objects.equals(id, assignQuestId.id);
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
