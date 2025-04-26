package com.gomo.app.member.application;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.model.QuestProperty;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.exception.MemberNotFoundException;
import com.gomo.app.member.exception.code.MemberErrorCode;
import com.gomo.app.member.presentation.response.ReadQuestPropertyResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadQuestPropertyUseCase {

	private final MemberRepository memberRepository;

	public ReadQuestPropertyResponse find(MemberId memberId) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.NOT_FOUND));

		QuestProperty questProperty = member.getQuestProperty();

		return ReadQuestPropertyResponse.of(questProperty);
	}
}
