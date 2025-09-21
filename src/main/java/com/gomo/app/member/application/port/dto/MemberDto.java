package com.gomo.app.member.application.port.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.member.domain.model.Member;

public record MemberDto(UUID id, String email, String handle, String name, String motto, QuestProperty questProperty, String roleType, String subscriptionPlan,
						String activateStatus, LocalDateTime signUpDateTime, LocalDateTime lastLoginDateTime, LocalDateTime deletedAt) {

	public static MemberDto from(Member member) {
		return new MemberDto(
			member.uuid(),
			member.getEmail().getEmail(),
			member.getHandle().getHandle(),
			member.getName().getName(),
			member.getMotto().getMotto(),
			QuestProperty.of(
				member.getQuestProperty().getDailyThreshold().getThreshold(),
				member.getQuestProperty().getWeeklyThreshold().getThreshold(),
				member.getQuestProperty().getMonthlyThreshold().getThreshold()
			),
			member.getRoleType().name(),
			member.getSubscriptionPlan().name(),
			member.getActivateStatus().name(),
			member.getSignUpDateTime(),
			member.getLastLoginDateTime(),
			member.getDeletedAt()
		);
	}

	public record QuestProperty(int dailyThreshold, int weeklyThreshold, int monthlyThreshold) {

		public static QuestProperty of(int dailyThreshold, int weeklyThreshold, int monthlyThreshold) {
			return new QuestProperty(dailyThreshold, weeklyThreshold, monthlyThreshold);
		}
	}
}
