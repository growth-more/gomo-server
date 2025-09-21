package com.gomo.app.quest.application.port;

import java.time.LocalDate;

import com.gomo.app.quest.domain.model.QuestType;

public interface FillQuestPoolPortIn {

	void fillForAllActiveParticipants(LocalDate lastLoginDateOfTargets, QuestType questType, int limit);
}
