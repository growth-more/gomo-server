package com.gomo.app.core.quest.domain.model;

import java.io.Serializable;
import java.util.UUID;

import com.gomo.app.common.arch.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Embeddable
@ValueObject
@EqualsAndHashCode
@ToString
public class QuestPoolId implements Serializable {

	private UUID id;

	protected QuestPoolId() {
	}

	private QuestPoolId(UUID id) {
		this.id = id;
	}

	public static QuestPoolId of(UUID id) {
		return new QuestPoolId(id);
	}
}
