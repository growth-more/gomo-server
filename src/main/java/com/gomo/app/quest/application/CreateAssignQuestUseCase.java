package com.gomo.app.quest.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.quest.domain.service.AssignQuestService;
import com.gomo.app.quest.presentation.request.CreateAssignQuestRequest;
import com.gomo.app.quest.presentation.response.CreateAssignQuestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateAssignQuestUseCase {

	private MemberRepository memberRepository;
	private AssignQuestService assignQuestService;

	public CreateAssignQuestResponse create(MemberId memberId, CreateAssignQuestRequest request) {
		return null;
	}
}
