package com.gomo.app.quest.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.presentation.response.ListAssignQuestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadAssignQuestUseCase {

	private AssignQuestRepository assignQuestRepository;

	public ListAssignQuestResponse findAll(MemberId memberId, QuestType questType) {
		return null;
	}
}
