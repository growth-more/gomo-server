package com.gomo.app.member.application.port.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.member.domain.model.Member;

public record MemberDto(UUID id, String email, String handle, String name, String motto, String profileImageUrl, String profileBannerUrl, String loginProvider,
						String roleType, String subscriptionPlan, String activateStatus, QuestPropertyDto questProperty, int availablePoints,
						LocalDateTime signUpDateTime, LocalDateTime lastLoginDateTime, LocalDateTime deletedAt) {

	public static MemberDto from(Member member, int availablePoints) {
		return new MemberDto(
			member.uuid(),
			member.getEmail().getEmail(),
			member.getHandle().getHandle(),
			member.getName().getName(),
			member.getMotto().getMotto(),
			member.getProfileImage().getUrl(),
			member.getProfileBanner().getUrl(),
			member.getLoginProvider().name(),
			member.getRoleType().name(),
			member.getSubscriptionPlan().name(),
			member.getActivateStatus().name(),
			QuestPropertyDto.from(member.getQuestProperty()),
			availablePoints,
			member.getSignUpDateTime(),
			member.getLastLoginDateTime(),
			member.getDeletedAt()
		);
	}
}
