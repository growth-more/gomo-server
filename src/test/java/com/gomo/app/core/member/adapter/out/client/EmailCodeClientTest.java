package com.gomo.app.core.member.adapter.out.client;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.support.auth.application.port.AuthCodeIssuer;
import com.gomo.app.support.auth.application.port.AuthCodeVerifier;

@DisplayName("[Adapter Unit]: 인증 코드 이메일 전송 요청 테스트")
@ExtendWith(MockitoExtension.class)
class EmailCodeClientTest {

	@InjectMocks
	private EmailCodeClient sut;

	@Mock
	private AuthCodeIssuer authCodeIssuer;

	@Mock
	private AuthCodeVerifier authCodeVerifier;

	@DisplayName("이메일 인증 코드 전송을 요청한다.")
	@Test
	void send_email_code() {
		String email = "email@gmail.com";
		sut.send(email);

		verify(authCodeIssuer, times(1)).sendToEmail(email);
	}

	@DisplayName("이메일 인증 코드 검증을 요청한다.")
	@Test
	void verify_email_code() {
		String email = "email@gmail.com";
		String authCode = "authCode";
		sut.verify(email, authCode);

		verify(authCodeVerifier, times(1)).verify(email, authCode);
	}
}
