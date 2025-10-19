package com.gomo.app.core.quest.domain.repository;

import java.util.List;

import com.gomo.app.core.quest.domain.model.assign.AssignQuest;

public interface BulkAssignQuestRepository {

	void saveAll(List<AssignQuest> assignQuests);
}
