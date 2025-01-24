package com.gomo.app.quest.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.quest.domain.service.RepeatQuestService;
import com.gomo.app.quest.presentation.request.CreateRepeatQuestRequest;
import com.gomo.app.quest.presentation.response.CreateRepeatQuestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateRepeatQuestUseCase {

	private final MemberRepository memberRepository;
	private final RepeatQuestService repeatQuestService;

	public CreateRepeatQuestResponse create(MemberId memberId, CreateRepeatQuestRequest request) {
		return null;
	}
}
