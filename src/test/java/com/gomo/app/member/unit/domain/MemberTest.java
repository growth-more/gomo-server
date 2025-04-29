package com.gomo.app.member.unit.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.model.LoginProvider;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.model.MemberName;
import com.gomo.app.member.domain.model.Motto;
import com.gomo.app.member.domain.model.Password;

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
}
