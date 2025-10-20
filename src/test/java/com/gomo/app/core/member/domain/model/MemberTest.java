package com.gomo.app.core.member.domain.model;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.exception.ActivateStatusException;
import com.gomo.app.core.member.exception.code.ActivateStatusErrorCode;
import com.gomo.app.core.member.fixture.MemberFixture;

@DisplayName("[Domain unit]: 회원 테스트")
@ExtendWith(MockitoExtension.class)
public class MemberTest {

	private static final UUID ID = UUID.randomUUID();
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

	@DisplayName("Memebr의 상태가 DELETED이다.")
	@Test
	void check_member_status_deleted() {
		Member member = MemberFixture.create(ActivateStatus.DELETED);
		assertThatThrownBy(member::validateActive)
			.isInstanceOf(ActivateStatusException.class)
			.hasMessageContaining(ActivateStatusErrorCode.DELETED.getMessage());

	}

	@DisplayName("Memebr의 상태가 BLOCKED이다.")
	@Test
	void check_member_status_blocked() {
		Member member = MemberFixture.create(ActivateStatus.BLOCKED);
		assertThatThrownBy(member::validateActive)
			.isInstanceOf(ActivateStatusException.class)
			.hasMessageContaining(ActivateStatusErrorCode.BLOCKED.getMessage());
	}

	@DisplayName("Memebr의 상태가 정상(ACTIVE)이다.")
	@Test
	void check_member_status_activated() {
		Member member = MemberFixture.create(ActivateStatus.ACTIVE);
		assertThatNoException().isThrownBy(member::validateActive);
	}

	@DisplayName("Memebr의 기본 위젯 스냅샷이 생성된다.")
	@Test
	void create_member_with_widget_snapshot() {
		Member member = MemberFixture.create();

		Widget actual = member.getWidget();

		assertThat(actual).isNotNull();
		assertThat(actual.getSnapshot()).isNotNull();
	}

	@DisplayName("위젯 스냅샷을 수정한다.")
	@Test
	void update_widget_snapshot() {
		Member member = MemberFixture.create();
		String updatedSnapshot = "{ \"test\": \"test_snapshot\" }";

		member.updateWidget(updatedSnapshot);

		assertThat(member.getWidget().getSnapshot()).isEqualTo(updatedSnapshot);
	}
}
