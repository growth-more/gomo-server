package com.gomo.app.core.quest.domain.model.participant;

import java.util.UUID;

import com.gomo.app.common.arch.ValueObject;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.exception.QuestConstraintViolationException;
import com.gomo.app.core.quest.domain.exception.code.QuestErrorCode;

import lombok.Getter;

@Getter
@ValueObject
public class Participant {

	private UUID id;
	private QuestQuota questQuota;

	protected Participant() {
	}

	private Participant(UUID id, QuestQuota questQuota) {
		this.id = id;
		this.questQuota = questQuota;
	}

	public static Participant of(UUID id, QuestQuota questQuota) {
		return new Participant(id, questQuota);
	}

	public void validateQuestQuota(QuestType questType, int currentQuestCount) {
		if (this.questQuota.isExceeded(questType, currentQuestCount)) {
			throw new QuestConstraintViolationException(QuestErrorCode.EXCEED_QUOTA);
		}
	}
}
