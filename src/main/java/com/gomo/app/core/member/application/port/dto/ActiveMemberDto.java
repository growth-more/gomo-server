package com.gomo.app.core.member.application.port.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.core.member.domain.model.Member;

public record ActiveMemberDto(UUID id, String roleType, String subscriptionPlan, LocalDateTime lastLoginDateTime, QuestPropertyDto questProperty) {

	public static ActiveMemberDto from(Member member) {
		return new ActiveMemberDto(
			member.uuid(),
			member.getRoleType().name(),
			member.getSubscriptionPlan().name(),
			member.getLastLoginDateTime(),
			QuestPropertyDto.from(member.getQuestProperty())
		);
	}
}
