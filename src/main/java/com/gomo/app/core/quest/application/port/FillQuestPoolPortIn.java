package com.gomo.app.core.quest.application.port;

import java.time.LocalDate;

import com.gomo.app.core.quest.domain.model.quest.QuestType;

public interface FillQuestPoolPortIn {

	void fillForAllActiveParticipants(LocalDate lastLoginDateOfTargets, QuestType questType, int limit);
}
