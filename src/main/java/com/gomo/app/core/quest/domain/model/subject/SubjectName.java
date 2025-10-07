package com.gomo.app.core.quest.domain.model.subject;

import com.gomo.app.common.arch.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class SubjectName {

	private String subjectName;

	protected SubjectName() {
	}

	private SubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public static SubjectName of(String subjectName) {
		return new SubjectName(subjectName);
	}

	public SubjectName update(String subjectName) {
		return new SubjectName(subjectName);
	}

	@Override
	public String toString() {
		return this.subjectName;
	}
}
