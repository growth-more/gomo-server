package com.gomo.app.member.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.model.QuestProperty;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.exception.MemberNotFoundException;
import com.gomo.app.member.presentation.response.ReadQuestPropertyResponse;

import lombok.RequiredArgsConstructor;

import static com.gomo.app.member.exception.MemberErrorCode.NOT_FOUND;

@RequiredArgsConstructor
@ApplicationService
public class ReadQuestPropertyUseCase {

	private final MemberRepository memberRepository;

	public ReadQuestPropertyResponse find(MemberId memberId) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException(NOT_FOUND, "member id not found: " + memberId));

		QuestProperty questProperty = member.getQuestProperty();

		return ReadQuestPropertyResponse.of(questProperty);
	}
}
