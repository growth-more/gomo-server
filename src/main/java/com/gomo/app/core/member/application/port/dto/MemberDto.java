package com.gomo.app.core.member.application.port.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.core.member.domain.model.Member;

public record MemberDto(UUID id, String email, String handle, String name, String motto, String profileImageUrl, String profileBannerUrl, String loginProvider,
						String roleType, String subscriptionPlan, String activateStatus, QuestPropertyDto questProperty, int availablePoints,
						LocalDateTime signUpDateTime, LocalDateTime lastLoginDateTime, LocalDateTime deletedAt, String widgetSnapshot) {

	public static MemberDto from(Member member, int availablePoints) {
		return new MemberDto(
			member.getId(),
			member.email(),
			member.handle(),
			member.name(),
			member.motto(),
			member.profileImageUrl(),
			member.profileBannerUrl(),
			member.getLoginProvider().name(),
			member.getRoleType().name(),
			member.getSubscriptionPlan().name(),
			member.getActivateStatus().name(),
			QuestPropertyDto.from(member.getQuestProperty()),
			availablePoints,
			member.getSignUpDateTime(),
			member.getLastLoginDateTime(),
			member.getDeletedAt(),
			member.getWidget().getSnapshot()
		);
	}
}
