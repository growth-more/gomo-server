package com.gomo.app.quest.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.dto.PageRequest;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.presentation.response.HistoryListAssignQuestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class HistoryReadAssignQuestUseCase {

	private AssignQuestRepository assignQuestRepository;

	public HistoryListAssignQuestResponse findAll(MemberId memberId, QuestType questType, PageRequest request) {
		return null;
	}
}
