package com.gomo.app.core.member.application.usecase;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.member.application.port.command.UpdateQuestPropertyCommand;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.model.QuestProperty;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
public class UpdateQuestPropertyUseCase {

	private final MemberService memberService;

	@AuditLog(action = "UPDATE_QUEST_PROPERTY")
	public void update(UpdateQuestPropertyCommand command) {
		Member member = memberService.find(command.memberId());
		QuestProperty questProperty = command.toDomain();
		member.updateQuestProperty(questProperty);
	}
}
