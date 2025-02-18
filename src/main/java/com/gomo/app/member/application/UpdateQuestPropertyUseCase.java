package com.gomo.app.member.application;

import com.gomo.app.member.domain.model.*;
import com.gomo.app.member.exception.MemberNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.presentation.request.UpdateQuestPropertyRequest;

import lombok.RequiredArgsConstructor;

import java.time.Month;

import static com.gomo.app.member.exception.MemberErrorCode.NOT_FOUND;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateQuestPropertyUseCase {

	private final MemberRepository memberRepository;

	public void update(MemberId memberId, UpdateQuestPropertyRequest request) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException(NOT_FOUND, "member id not found: " + memberId));

		QuestProperty questProperty = request.toDomain();
		member.updateQuestProperty(questProperty);
	}
}
