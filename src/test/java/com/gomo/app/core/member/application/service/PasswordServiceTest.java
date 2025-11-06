package com.gomo.app.core.member.application.service;

import static com.gomo.app.core.member.domain.exception.code.MemberErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.application.port.out.PasswordEncodeManager;
import com.gomo.app.core.member.domain.exception.MemberAuthenticationFailedException;
import com.gomo.app.core.member.domain.model.LoginProvider;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.model.Password;
import com.gomo.app.core.member.fixture.MemberFixture;

@DisplayName("[Application unit] : 비밀번호 초기화 테스트")
@ExtendWith(MockitoExtension.class)
class PasswordServiceTest {

	@InjectMocks
	private PasswordService sut;

	@Mock
	private PasswordEncodeManager passwordEncodeManager;

	@Mock
	private MemberService memberService;

	@DisplayName("비밀번호를 초기화한다.")
	@Test
	void reset_password() {
		Member member = MemberFixture.create();
		String encoded = "encoded_password";
		doReturn(member).when(memberService).findByEmail(any());
		doReturn(encoded).when(passwordEncodeManager).encode(any());

		sut.reset(member.email(), "New1234@");

		assertThat(member.password()).isEqualTo(encoded);
	}

	@DisplayName("비밀번호를 변경한다.")
	@Test
	void update_password() {
		Member member = MemberFixture.create();
		String encoded = "encoded_password";
		doReturn(member).when(memberService).findById(any());
		doReturn(true).when(passwordEncodeManager).verify(any(), any());
		doReturn(encoded).when(passwordEncodeManager).encode(any());

		sut.update(member.getId(), "Origin123@", "New1234@");

		assertThat(member.password()).isEqualTo(encoded);
	}

	@DisplayName("비밀번호를 검증한다.")
	@Test
	void verify_password() {
		doReturn(true).when(passwordEncodeManager).verify(any(), any());

		assertThatCode(() -> sut.verify(MemberFixture.create(), "Origin123@")).doesNotThrowAnyException();
	}

	@DisplayName("기존 비밀번호 불일치로 비밀번호 검증에 실패한다.")
	@Test
	void verify_password_with_wrong_password() {
		doReturn(false).when(passwordEncodeManager).verify(any(), any());

		assertThatThrownBy(() -> sut.verify(MemberFixture.create(), "Origin123@"))
			.isInstanceOf(MemberAuthenticationFailedException.class)
			.hasMessageContaining(AUTHENTICATION_FAILED.getMessage());
	}

	@DisplayName("이메일로 가입한 회원의 비밀번호를 암호화한다.")
	@Test
	void encode_password_with_email_member() {
		String password = "Test123@";
		String encoded = "encoded";
		doReturn(encoded).when(passwordEncodeManager).encode(password);

		Password actual = sut.encode(LoginProvider.EMAIL.name(), password, UUID.randomUUID());

		assertThat(actual.getPassword()).isEqualTo(encoded);
	}

	@DisplayName("OAuth로 가입한 회원의 비밀번호를 암호화한다.")
	@Test
	void encode_password_with_oauth_member() {
		UUID memberId = UUID.randomUUID();
		Password oauthPassword = Password.forOAuth(memberId.toString());
		String encoded = "encoded";
		doReturn(encoded).when(passwordEncodeManager).encode(oauthPassword.getPassword());

		Password actual = sut.encode(LoginProvider.NAVER.name(), "Test123@", memberId);

		assertThat(actual.getPassword()).isEqualTo(encoded);
	}
}
