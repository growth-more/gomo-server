package com.gomo.app.core.member.application.service;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.member.application.port.command.UpdateQuestPropertyCommand;
import com.gomo.app.core.member.application.port.dto.QuestPropertyDto;
import com.gomo.app.core.member.application.port.in.QuestPropertyReader;
import com.gomo.app.core.member.application.port.in.QuestPropertyUpdater;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.model.QuestProperty;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class QuestPropertyService implements QuestPropertyReader, QuestPropertyUpdater {

	private final MemberService memberService;

	@Override
	@Transactional(readOnly = true)
	public QuestPropertyDto read(UUID memberId) {
		Member member = memberService.findById(memberId);
		QuestProperty questProperty = member.getQuestProperty();
		return QuestPropertyDto.from(questProperty);
	}

	@Override
	@AuditLog(action = "QUEST_PROPERTY_UPDATE")
	public void update(UpdateQuestPropertyCommand command) {
		Member member = memberService.findById(command.memberId());
		QuestProperty questProperty = command.toDomain();
		member.updateQuestProperty(questProperty);
	}
}
