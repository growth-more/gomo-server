package com.gomo.app.support.auth.adapter.out.client;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.application.port.in.MemberLoginProcessor;
import com.gomo.app.core.member.application.port.in.MemberOAuthLoginProcessor;

@DisplayName("[Adapter unit]: 인증 요청 테스트")
@ExtendWith(MockitoExtension.class)
class MemberClientTest {

	@InjectMocks
	private MemberClient sut;

	@Mock
	private MemberLoginProcessor memberLoginProcessor;

	@Mock
	private MemberOAuthLoginProcessor memberOAuthLoginProcessor;

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
