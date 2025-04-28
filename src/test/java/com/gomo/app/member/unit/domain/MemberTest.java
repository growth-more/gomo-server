package com.gomo.app.member.unit.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.model.LoginProvider;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.model.MemberName;
import com.gomo.app.member.domain.model.Motto;
import com.gomo.app.member.domain.model.Password;
import com.gomo.app.member.exception.QuestPropertyConstraintViolationException;
import com.gomo.app.member.exception.code.QuestPropertyErrorCode;
import com.gomo.app.quest.domain.model.QuestType;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Domain unit]: 회원 테스트")
public class MemberTest {

	private static final MemberId ID = MemberId.of(UUID.randomUUID());
	private static final Email EMAIL = Email.of("test@gmail.com");
	private static final Handle HANDLE = Handle.of("@tester");
	private static final MemberName MEMBER_NAME = MemberName.of("Tester");
	private static final Motto MOTTO = Motto.of("mottoTest");
	private static final LoginProvider LOGIN_PROVIDER = LoginProvider.EMAIL;

	@DisplayName("회원을 생성한다.")
	@Test
	void create_member() {
		Member member = Member.of(ID, EMAIL, Mockito.mock(Password.class), HANDLE, MEMBER_NAME, MOTTO, LOGIN_PROVIDER);

		assertThat(member)
			.extracting("id", "email", "handle", "motto", "loginProvider")
			.containsExactly(ID, EMAIL, HANDLE, MOTTO, LOGIN_PROVIDER);
	}

	@DisplayName("일일 퀘스트 개수가 퀘스트 제한에 도달하지 않는다.")
	@Test
	void not_exceed_daily_quest_threshold() {
		Member member = MemberFixture.member(3);

		boolean actual = member.hasReachedQuestThreshold(QuestType.DAILY.name(), 2);

		assertThat(actual).isFalse();
	}

	@DisplayName("일일 퀘스트 개수가 퀘스트 제한에 도달한다.")
	@Test
	void exceed_daily_quest_threshold() {
		Member member = MemberFixture.member(3);

		boolean actual = member.hasReachedQuestThreshold(QuestType.DAILY.name(), 3);

		assertThat(actual).isTrue();
	}

	@DisplayName("주간 퀘스트 개수가 퀘스트 제한에 도달하지 않는다.")
	@Test
	void not_exceed_weekly_quest_threshold() {

		Member member = MemberFixture.member(3);

		boolean actual = member.hasReachedQuestThreshold(QuestType.WEEKLY.name(), 2);

		assertThat(actual).isFalse();
	}

	@DisplayName("주간 퀘스트 개수가 퀘스트 제한에 도달한다.")
	@Test
	void exceed_weekly_quest_threshold() {
		Member member = MemberFixture.member(3);

		boolean actual = member.hasReachedQuestThreshold(QuestType.WEEKLY.name(), 3);

		assertThat(actual).isTrue();
	}

	@DisplayName("월간 퀘스트 개수가 퀘스트 제한에 도달하지 않는다.")
	@Test
	void not_exceed_monthly_quest_threshold() {
		Member member = MemberFixture.member(3);

		boolean actual = member.hasReachedQuestThreshold(QuestType.MONTHLY.name(), 2);

		assertThat(actual).isFalse();
	}

	@DisplayName("월간 퀘스트 개수가 퀘스트 제한에 도달한다.")
	@Test
	void exceed_monthly_quest_threshold() {
		Member member = MemberFixture.member(3);

		boolean actual = member.hasReachedQuestThreshold(QuestType.MONTHLY.name(), 3);

		assertThat(actual).isTrue();
	}

	@DisplayName("잘못된 퀘스트 타입을 제공한다.")
	@Test
	void exceed_quest_threshold() {
		Member member = MemberFixture.member(3);

		assertThatThrownBy(() -> member.hasReachedQuestThreshold("NONE", 3))
			.isInstanceOf(QuestPropertyConstraintViolationException.class)
			.hasMessageContaining(QuestPropertyErrorCode.UNEXPECTED_QUEST_TYPE.getMessage());
	}
}
