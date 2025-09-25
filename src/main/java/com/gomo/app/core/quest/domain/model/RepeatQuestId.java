package com.gomo.app.core.quest.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import com.gomo.app.common.arch.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class RepeatQuestId implements Serializable {

    private UUID id;

    protected RepeatQuestId() {
    }

    private RepeatQuestId(UUID id) {
        this.id = id;
    }

    public static RepeatQuestId of(UUID id) {
        return new RepeatQuestId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RepeatQuestId repeatQuestId = (RepeatQuestId)o;
        return Objects.equals(id, repeatQuestId.id);
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
