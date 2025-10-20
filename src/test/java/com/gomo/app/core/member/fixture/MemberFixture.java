package com.gomo.app.core.member.fixture;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.core.member.domain.model.ActivateStatus;
import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.domain.model.Handle;
import com.gomo.app.core.member.domain.model.LoginProvider;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.model.MemberName;
import com.gomo.app.core.member.domain.model.Motto;
import com.gomo.app.core.member.domain.model.Password;
import com.gomo.app.core.member.domain.model.ProfileBanner;
import com.gomo.app.core.member.domain.model.ProfileImage;
import com.gomo.app.core.member.domain.model.QuestProperty;
import com.gomo.app.core.member.domain.model.RoleType;
import com.gomo.app.core.member.domain.model.SubscriptionPlan;
import com.gomo.app.core.member.domain.model.Widget;

public class MemberFixture {

	public static Member create() {
		return Member.of(
			UUID.randomUUID(),
			Email.of("test@naver.com"),
			Password.ofEncoded("Test123!"),
			Handle.of("@gomo"),
			MemberName.of("testname"),
			Motto.of("test motto"),
			LoginProvider.EMAIL
		);
	}

	public static Member create(int dailyQuestThreshold) {
		return new Member(
			UUID.randomUUID(),
			Email.of("test@naver.com"),
			Password.ofEncoded("Test123!"),
			Handle.of("@gomo"),
			MemberName.of("testname"),
			Motto.of("test motto"),
			ProfileImage.createDefault(),
			ProfileBanner.createDefault(),
			QuestProperty.of(dailyQuestThreshold, 5, 5),
			LoginProvider.EMAIL,
			RoleType.ROLE_MEMBER,
			SubscriptionPlan.FREE,
			ActivateStatus.ACTIVE,
			LocalDateTime.now(),
			null,
			Widget.createDefault()
		);
	}

	public static Member create(int dailyQuestThreshold, int weeklyQuestThreshold, int monthlyQuestThreshold) {
		return new Member(
			UUID.randomUUID(),
			Email.of("test@naver.com"),
			Password.ofEncoded("Test123!"),
			Handle.of("@gomo"),
			MemberName.of("testname"),
			Motto.of("test motto"),
			ProfileImage.createDefault(),
			ProfileBanner.createDefault(),
			QuestProperty.of(dailyQuestThreshold, weeklyQuestThreshold, monthlyQuestThreshold),
			LoginProvider.EMAIL,
			RoleType.ROLE_MEMBER,
			SubscriptionPlan.FREE,
			ActivateStatus.ACTIVE,
			LocalDateTime.now(),
			null,
			Widget.createDefault()
		);
	}

	public static Member create(SubscriptionPlan subscriptionPlan) {
		return new Member(
			UUID.randomUUID(),
			Email.of("test@naver.com"),
			Password.ofEncoded("Test123!"),
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
			null,
			Widget.createDefault()
		);
	}

	public static Member create(ActivateStatus status) {
		return new Member(
			UUID.randomUUID(),
			Email.of("test@naver.com"),
			Password.ofEncoded("Test123!"),
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
			null,
			Widget.createDefault()
		);
	}
}
