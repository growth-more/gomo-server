package com.gomo.app.quest.domain.model;

import java.util.UUID;

import com.gomo.app.common.ValueObject;
import com.gomo.app.quest.exception.QuestConstraintViolationException;
import com.gomo.app.quest.exception.code.QuestErrorCode;

import lombok.Getter;

@Getter
@ValueObject
public class Participant {

	private ParticipantId id;
	private QuestQuota questQuota;

	protected Participant() {
	}

	private Participant(
		ParticipantId id,
		QuestQuota questQuota
	) {
		this.id = id;
		this.questQuota = questQuota;
	}

	public UUID uuid() {
		return this.id.getId();
	}

	public static Participant of(
		ParticipantId id,
		QuestQuota questQuota
	) {
		return new Participant(id, questQuota);
	}

	public void validateQuestQuota(QuestType questType, int currentQuestCount) {
		if (this.questQuota.isExceeded(questType, currentQuestCount)) {
			throw new QuestConstraintViolationException(QuestErrorCode.EXCEED_QUOTA);
		}
	}
}
