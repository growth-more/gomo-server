package com.gomo.app.quest.application;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class ConfirmAssignQuestUseCase {

	private AssignQuestRepository assignQuestRepository;

	public void confirm(MemberId memberId, AssignQuestId assignQuestId) {

	}
}
