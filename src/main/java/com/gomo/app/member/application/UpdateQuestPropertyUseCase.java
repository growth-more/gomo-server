package com.gomo.app.member.application;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.logging.AuditLog;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.model.QuestProperty;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.presentation.request.UpdateQuestPropertyRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateQuestPropertyUseCase {

	private final MemberService memberService;

	@AuditLog(action = "UPDATE_QUEST_PROPERTY")
	public void update(UUID memberId, UpdateQuestPropertyRequest request) {
		Member member = memberService.find(MemberId.of(memberId));
		QuestProperty questProperty = request.toDomain();
		member.updateQuestProperty(questProperty);
	}
}
