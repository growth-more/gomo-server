package com.gomo.app.core.quest.domain.model.subject;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import com.gomo.app.common.arch.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class SubjectId implements Serializable {

	private UUID id;

	protected SubjectId() {
	}

	private SubjectId(UUID id) {
		this.id = id;
	}

	public static SubjectId of(UUID id) {
		return new SubjectId(id);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		SubjectId subjectId = (SubjectId)o;
		return Objects.equals(id, subjectId.id);
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
