package com.gomo.app.support.auth.adapter.out.client;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.application.port.in.EmailChecker;
import com.gomo.app.core.member.application.port.in.MemberCreator;
import com.gomo.app.core.member.application.port.in.MemberLoginProcessor;
import com.gomo.app.core.member.application.port.in.MemberOAuthLoginProcessor;
import com.gomo.app.support.auth.application.port.command.CreatePrincipalCommand;

@DisplayName("[Adapter unit]: 인증 요청 테스트")
@ExtendWith(MockitoExtension.class)
class MemberClientTest {

	@InjectMocks
	private MemberClient sut;

	@Mock
	private MemberCreator memberCreator;

	@Mock
	private EmailChecker emailChecker;

	@Mock
	private MemberLoginProcessor memberLoginProcessor;

	@Mock
	private MemberOAuthLoginProcessor memberOAuthLoginProcessor;

	@DisplayName("회원 생성을 요청한다.")
	@Test
	void create_principal() {
		CreatePrincipalCommand command = CreatePrincipalCommand.of("test@email.com", "password", "handle", "name", "motto", "loginProvider", "temporaryToken");
		sut.create(command);

		verify(memberCreator, times(1)).create(any());
	}

	@DisplayName("이메일 존재 여부를 확인한다.")
	@Test
	void exists_email() {
		String email = "email";
		sut.exists(email);

		verify(emailChecker, times(1)).exists(email);
	}

	@DisplayName("일반 회원 로그인 요청한다.")
	@Test
	void login() {
		String email = "email";
		String password = "password";
		sut.login(email, password);

		verify(memberLoginProcessor, times(1)).login(email, password);
	}

	@DisplayName("OAuth 회원 로그인 요청한다.")
	@Test
	void oauth_login() {
		String email = "email";
		sut.login(email);

		verify(memberOAuthLoginProcessor, times(1)).login(email);
	}
}
