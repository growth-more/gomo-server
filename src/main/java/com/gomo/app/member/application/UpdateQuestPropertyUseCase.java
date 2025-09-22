package com.gomo.app.member.application;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.logging.AuditLog;
import com.gomo.app.member.application.port.command.UpdateQuestPropertyCommand;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.model.QuestProperty;
import com.gomo.app.member.domain.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateQuestPropertyUseCase {

	private final MemberService memberService;

	@AuditLog(action = "UPDATE_QUEST_PROPERTY")
	public void update(UpdateQuestPropertyCommand command) {
		Member member = memberService.find(MemberId.of(command.memberId()));
		QuestProperty questProperty = command.toDomain();
		member.updateQuestProperty(questProperty);
	}
}
