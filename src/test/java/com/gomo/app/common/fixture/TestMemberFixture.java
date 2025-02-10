package com.gomo.app.common.fixture;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.gomo.app.member.domain.model.ActivateStatus;
import com.gomo.app.member.domain.model.DailyThreshold;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.model.MemberName;
import com.gomo.app.member.domain.model.MonthlyThreshold;
import com.gomo.app.member.domain.model.Motto;
import com.gomo.app.member.domain.model.Password;
import com.gomo.app.member.domain.model.ProfileImage;
import com.gomo.app.member.domain.model.QuestProperty;
import com.gomo.app.member.domain.model.RoleType;
import com.gomo.app.member.domain.model.SubscriptionPlan;
import com.gomo.app.member.domain.model.WeeklyThreshold;

/**
 * 테스트 사용자
 * 실제 데이터베이스에 존재하는 테스트 레코드와 동일한 값을 수동으로 지정해서 사용한다.
 * TODO <jhl221123>: 비밀번호를 암호화하면 수정이 필요하다.
 */
@Component
public class TestMemberFixture {

	private static final String ID = "a10581ce-d721-11ef-a8a5-2508e2a6438b";
	private static final String EMAIL = "gomotest@naver.com";
	private static final String PASSWORD = "gomotest1234@";
	private static final String HANDLE = "@GOMOTEST";
	private static final String NAME = "gomotest";
	private static final String MOTTO = "gomotest fighting!";
	private static final String PROFILE_IMAGE_URL = "https://mini-cloud/gomotest-profile";
	private static final String PROFILE_IMAGE_NAME = "gomotest-profile.png";
	private static final int DAILY_THRESHOLD = 10;
	private static final int WEEKLY_THRESHOLD = 5;
	private static final int MONTHLY_THRESHOLD = 3;
	private static final RoleType ROLE_TYPE = RoleType.ROLE_MEMBER;
	private static final SubscriptionPlan SUBSCRIPTION_PLAN = SubscriptionPlan.FREE;
	private static final ActivateStatus ACTIVATE_STATUS = ActivateStatus.ACTIVE;
	private static final String SIGN_UP_DATE_TIME = "2025-01-20T20:36:37.591469";
	private static final int AVAILABLE_POINTS = 1660;

	public static Member testMember() {
		return new Member(
			MemberId.of(UUID.fromString(ID)),
			Email.of(EMAIL),
			Password.of(PASSWORD),
			Handle.of(HANDLE),
			MemberName.of(NAME),
			Motto.of(MOTTO),
			new ProfileImage(PROFILE_IMAGE_URL, PROFILE_IMAGE_NAME),
			new QuestProperty(
				DailyThreshold.of(DAILY_THRESHOLD),
				WeeklyThreshold.of(WEEKLY_THRESHOLD),
				MonthlyThreshold.of(MONTHLY_THRESHOLD)),
			ROLE_TYPE,
			SUBSCRIPTION_PLAN,
			ACTIVATE_STATUS,
			LocalDateTime.parse(SIGN_UP_DATE_TIME),
			null
		);
	}

	public static MemberId id() {
		return MemberId.of(UUID.fromString(ID));
	}

	public static String email() {
		return EMAIL;
	}

	public static String password() {
		return PASSWORD;
	}

	public static String handle() {
		return HANDLE;
	}

	public static String name() {
		return NAME;
	}

	public static String motto() {
		return MOTTO;
	}

	public static String profileImageUrl() {
		return PROFILE_IMAGE_URL;
	}

	public static String profileImageName() {
		return PROFILE_IMAGE_NAME;
	}

	public static int dailyThreshold() {
		return DAILY_THRESHOLD;
	}

	public static int weeklyThreshold() {
		return WEEKLY_THRESHOLD;
	}

	public static int monthlyThreshold() {
		return MONTHLY_THRESHOLD;
	}

	public static RoleType roleType() {
		return ROLE_TYPE;
	}

	public static SubscriptionPlan subscriptionPlan() {
		return SUBSCRIPTION_PLAN;
	}

	public static ActivateStatus activateStatus() {
		return ACTIVATE_STATUS;
	}

	public static String signUpDateTime() {
		return SIGN_UP_DATE_TIME;
	}

	public static int availablePoints() {
		return AVAILABLE_POINTS;
	}
}
