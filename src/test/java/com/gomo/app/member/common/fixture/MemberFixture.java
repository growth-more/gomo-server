package com.gomo.app.member.common.fixture;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.member.domain.model.*;
import com.gomo.app.member.domain.service.PasswordService;

public class MemberFixture {

	public static Member member(PasswordService passwordService) {
		return Member.of(
			MemberId.of(UUID.randomUUID()),
			Email.of("test@naver.com"),
			Password.of("Test123!", passwordService),
			Handle.of("@gomo"),
			MemberName.of("testname"),
			Motto.of("test motto"),
			LoginProvider.EMAIL
		);
	}

	public static Member memberFromGoogle() {
		return Member.of(
			MemberId.of(UUID.randomUUID()),
			Email.of("test@naver.com"),
			null,
			null,
			null,
			null,
			LoginProvider.GOOGLE
		);
	}

	public static Member member(int dailyQuestThreshold, PasswordService passwordService) {
		return new Member(
			MemberId.of(UUID.randomUUID()),
			Email.of("test@naver.com"),
			Password.of("Test123!", passwordService),
			Handle.of("@gomo"),
			MemberName.of("testname"),
			Motto.of("test motto"),
			ProfileImage.createDefault(),
			ProfileBanner.createDefault(),
			new QuestProperty(DailyThreshold.of(dailyQuestThreshold), WeeklyThreshold.createDefault(), MonthlyThreshold.createDefault()),
			LoginProvider.EMAIL,
			RoleType.ROLE_MEMBER,
			SubscriptionPlan.FREE,
			ActivateStatus.ACTIVE,
			LocalDateTime.now(),
			null
		);
	}

	public static Member member(ActivateStatus status, PasswordService passwordService) {
		return new Member(
			MemberId.of(UUID.randomUUID()),
			Email.of("test@naver.com"),
			Password.of("Test123!", passwordService),
			Handle.of("@gomo"),
			MemberName.of("testname"),
			Motto.of("test motto"),
			ProfileImage.createDefault(),
			ProfileBanner.createDefault(),
			QuestProperty.createDefault(),
			LoginProvider.EMAIL,
			RoleType.ROLE_MEMBER,
			SubscriptionPlan.FREE,
			status,
			LocalDateTime.now(),
			null
		);
	}
}
