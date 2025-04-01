package com.gomo.app.member.unit.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.gomo.app.common.exception.PolicyViolationException;
import com.gomo.app.member.domain.model.*;
import com.gomo.app.member.domain.service.PasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.quest.domain.model.QuestType;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Domain unit]: 회원 테스트")
public class MemberTest {

	@Mock
	PasswordService passwordService;
	private Password PASSWORD;

	@BeforeEach
	void setUp(){
		PASSWORD = Password.of("Test1234!", passwordService);
	}

	private static final MemberId ID = MemberId.of(UUID.randomUUID());
	private static final Email EMAIL = Email.of("test@gmail.com");
	private static final Handle HANDLE = Handle.of("@tester");
	private static final MemberName MEMBER_NAME = MemberName.of("Tester");
	private static final Motto MOTTO = Motto.of("mottoTest");
	private static final LoginProvider LOGIN_PROVIDER = LoginProvider.EMAIL;

	@DisplayName("회원을 생성한다.")
	@Test
	void create_member(){
		Member member = Member.of(ID, EMAIL, PASSWORD, HANDLE, MEMBER_NAME, MOTTO, LOGIN_PROVIDER);

		assertThat(member)
				.extracting("id", "email", "password", "handle", "motto", "loginProvider")
				.containsExactly(ID, EMAIL, PASSWORD, HANDLE, MOTTO, LOGIN_PROVIDER);
	}

	@DisplayName("프로필 이미지를 등록하지 않으면 기본 이미지가 등록된다.")
	@Test
	void create_member_with_default_profile(){
		Member member = Member.of(ID, EMAIL, PASSWORD, HANDLE, MEMBER_NAME, MOTTO, LOGIN_PROVIDER);

		assertThat(member.getProfileImage().getUrl())
				.isEqualTo("https://image.nurdykim.me/gomo/default-image.png");
	}

	@DisplayName("일일 퀘스트 개수가 퀘스트 제한에 도달하지 않는다.")
	@Test
	void not_exceed_daily_quest_threshold() {

		Member member = MemberFixture.member(3, passwordService);

		boolean actual = member.hasReachedQuestThreshold(QuestType.DAILY.name(), 2);

		assertThat(actual).isFalse();
	}

	@DisplayName("일일 퀘스트 개수가 퀘스트 제한에 도달한다.")
	@Test
	void exceed_daily_quest_threshold() {
		Member member = MemberFixture.member(3, passwordService);

		boolean actual = member.hasReachedQuestThreshold(QuestType.DAILY.name(), 3);

		assertThat(actual).isTrue();
	}

	@DisplayName("주간 퀘스트 개수가 퀘스트 제한에 도달하지 않는다.")
	@Test
	void not_exceed_weekly_quest_threshold() {

		Member member = MemberFixture.member(3, passwordService);

		boolean actual = member.hasReachedQuestThreshold(QuestType.WEEKLY.name(), 2);

		assertThat(actual).isFalse();
	}

	@DisplayName("주간 퀘스트 개수가 퀘스트 제한에 도달한다.")
	@Test
	void exceed_weekly_quest_threshold() {
		Member member = MemberFixture.member(3, passwordService);

		boolean actual = member.hasReachedQuestThreshold(QuestType.WEEKLY.name(), 3);

		assertThat(actual).isTrue();
	}

	@DisplayName("월간 퀘스트 개수가 퀘스트 제한에 도달하지 않는다.")
	@Test
	void not_exceed_monthly_quest_threshold() {

		Member member = MemberFixture.member(3, passwordService);

		boolean actual = member.hasReachedQuestThreshold(QuestType.MONTHLY.name(), 2);

		assertThat(actual).isFalse();
	}

	@DisplayName("월간 퀘스트 개수가 퀘스트 제한에 도달한다.")
	@Test
	void exceed_monthly_quest_threshold() {
		Member member = MemberFixture.member(3, passwordService);

		boolean actual = member.hasReachedQuestThreshold(QuestType.MONTHLY.name(), 3);

		assertThat(actual).isTrue();
	}

	@DisplayName("잘못된 퀘스트 타입을 제공한다.")
	@Test
	void exceed_quest_threshold() {
		Member member = MemberFixture.member(3, passwordService);

		assertThatThrownBy(() -> member.hasReachedQuestThreshold("NONE", 3))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("Invalid quest type: NONE");
	}

	@DisplayName("비밀번호수정 시 기존 비밀번호와 동일한 비밀번호로 수정할 수 없다.")
    @Test
    void update_password_with_same_exist_password(){
        Member member = MemberFixture.member(passwordService);

        assertThatThrownBy(() -> member.updatePassword("Test1234!", "Test1234!", passwordService))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("update password must not same as origin password");
    }
}
