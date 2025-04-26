package com.gomo.app.member.application;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.model.QuestProperty;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.exception.MemberNotFoundException;
import com.gomo.app.member.exception.code.MemberErrorCode;
import com.gomo.app.member.presentation.request.UpdateQuestPropertyRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateQuestPropertyUseCase {

	private final MemberRepository memberRepository;

	public void update(MemberId memberId, UpdateQuestPropertyRequest request) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.NOT_FOUND));

		QuestProperty questProperty = request.toDomain();
		member.updateQuestProperty(questProperty);
	}
}
