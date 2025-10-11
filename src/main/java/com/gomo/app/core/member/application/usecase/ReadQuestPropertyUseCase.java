package com.gomo.app.core.member.application.usecase;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.member.application.port.dto.QuestPropertyDto;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.model.QuestProperty;
import com.gomo.app.core.member.domain.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadQuestPropertyUseCase {

	private final MemberService memberService;

	public QuestPropertyDto find(UUID memberId) {
		Member member = memberService.find(memberId);
		QuestProperty questProperty = member.getQuestProperty();
		return QuestPropertyDto.from(questProperty);
	}
}
