package com.gomo.app.member.common.fixture;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.member.domain.model.ActivateStatus;
import com.gomo.app.member.domain.model.DailyThreshold;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.model.LoginProvider;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.model.MemberName;
import com.gomo.app.member.domain.model.MonthlyThreshold;
import com.gomo.app.member.domain.model.Motto;
import com.gomo.app.member.domain.model.Password;
import com.gomo.app.member.domain.model.ProfileBanner;
import com.gomo.app.member.domain.model.ProfileImage;
import com.gomo.app.member.domain.model.QuestProperty;
import com.gomo.app.member.domain.model.RoleType;
import com.gomo.app.member.domain.model.SubscriptionPlan;
import com.gomo.app.member.domain.model.WeeklyThreshold;

public class MemberFixture {

	public static Member member() {
		return Member.of(
			MemberId.of(UUID.randomUUID()),
			Email.of("test@naver.com"),
			new Password("Test123!"),
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

	public static Member member(int dailyQuestThreshold) {
		return new Member(
			MemberId.of(UUID.randomUUID()),
			Email.of("test@naver.com"),
			new Password("Test123!"),
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

	public static Member member(SubscriptionPlan subscriptionPlan) {
		return new Member(
			MemberId.of(UUID.randomUUID()),
			Email.of("test@naver.com"),
			new Password("Test123!"),
			Handle.of("@gomo"),
			MemberName.of("testname"),
			Motto.of("test motto"),
			ProfileImage.createDefault(),
			ProfileBanner.createDefault(),
			QuestProperty.createDefault(),
			LoginProvider.EMAIL,
			RoleType.ROLE_MEMBER,
			subscriptionPlan,
			ActivateStatus.ACTIVE,
			LocalDateTime.now(),
			null
		);
	}

	public static Member member(ActivateStatus status) {
		return new Member(
			MemberId.of(UUID.randomUUID()),
			Email.of("test@naver.com"),
			new Password("Test123!"),
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
