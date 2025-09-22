package com.gomo.app.member.application;

import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.application.port.dto.QuestPropertyDto;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.model.QuestProperty;
import com.gomo.app.member.domain.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadQuestPropertyUseCase {

	private final MemberService memberService;

	public QuestPropertyDto find(UUID memberId) {
		Member member = memberService.find(MemberId.of(memberId));
		QuestProperty questProperty = member.getQuestProperty();
		return QuestPropertyDto.from(questProperty);
	}
}
